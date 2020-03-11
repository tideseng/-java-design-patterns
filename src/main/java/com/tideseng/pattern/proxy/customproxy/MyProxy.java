package com.tideseng.pattern.proxy.customproxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

//用代码编写代码、用代码编译代码、用代码加载代码、用代码运行代码

/**
 * 用来生成源代码的工具类
 */
public class MyProxy {

    public static final String ln = "\r\n";

    public static Object newProxyInstance(MyClassLoader classLoader, Class<?> [] interfaces, MyInvocationHandler h){

        try {
            //1、动态生成源代码.java文件
            String src = generateSrc(interfaces);

            //2、Java文件输出磁盘
            // toURI().getPath()防止路径空格、“+”和中文导致的异常
            String filePath = MyProxy.class.getResource("").toURI().getPath();
            System.out.println(filePath);
            File f = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(f);
            fw.write(src);
            fw.flush();
            fw.close();

            //3、把生成的.java文件编译成.class文件
            JavaCompiler compiler;
            compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manage = compiler.getStandardFileManager(null,null,null);
            Iterable iterable = manage.getJavaFileObjects(f);

            // 如果出现java.lang.ClassNotFoundException: com.sun.tools.javac.processing.JavacProcessingEnvironment错误，
            // 但程序能继续运行，是因为lombok于jdk有冲突，将lombok的scope改为provided即可
            JavaCompiler.CompilationTask task = compiler.getTask(null,manage,null,null,null, iterable);
            task.call();
            manage.close();

            //4、编译生成的.class文件加载到JVM中来
            Class proxyClass =  classLoader.findClass("$Proxy0");
            Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);
            f.delete();

            //5、返回字节码重组以后的新的代理对象
            return c.newInstance(h);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用代码写代码
     * @param interfaces
     * @return
     */
    private static String generateSrc(Class<?>[] interfaces){
        StringBuffer sb = new StringBuffer();
        sb.append("package com.tideseng.pattern.proxy.customproxy;" + ln);
        sb.append("import com.tideseng.pattern.proxy.Person;" + ln);
        sb.append("import java.lang.reflect.*;" + ln);
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);

            sb.append("MyInvocationHandler h;" + ln);
            sb.append("public $Proxy0(MyInvocationHandler h) { " + ln);
                sb.append("this.h = h;");
            sb.append("}" + ln);
    
            for (Method m : interfaces[0].getMethods()){
                /*sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "() {" + ln);
                    sb.append("try{" + ln);
                        sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\",new Class[]{});" + ln);
                        sb.append("this.h.invoke(this,m,null);" + ln);
                    sb.append("}catch(Throwable e){" + ln);
                        sb.append("e.printStackTrace();" + ln);
                    sb.append("}");
                sb.append("}");*/
                
                Class<?>[] params = m.getParameterTypes();

                StringBuffer paramNames = new StringBuffer();
                StringBuffer paramValues = new StringBuffer();
                StringBuffer paramClasses = new StringBuffer();

                for(int i=0; i<params.length; i++){
                    Class clazz = params[i];
                    String type = clazz.getName();
                    String paramName = toLowerFirstCase(clazz.getSimpleName());
                    paramNames.append(type +" "+ paramName);
                    paramValues.append(paramName);
                    paramClasses.append(clazz.getName() +".class");
                    if(i >0&& i < params.length-1){
                        paramNames.append(",");
                        paramClasses.append(",");
                        paramValues.append(",");
                    }
                }

                sb.append("public "+ m.getReturnType().getName() +" "+ m.getName() +"("+ paramNames.toString() +") {"+ ln);
                    sb.append("try{"+ ln);
                        sb.append("Method m = "+ interfaces[0].getName() +".class.getMethod(\""+ m.getName() +"\",new Class[]{"+ paramClasses.toString() +"});"+ ln);
                        sb.append((hasReturnValue(m.getReturnType()) ?"return ("+ m.getReturnType().getName() +")":"") + getCaseCode("this.h.invoke(this,m,new Object[]{"+ paramValues +"})",m.getReturnType()) +";"+ ln);
                    sb.append("}catch(Error _ex) {");
                    sb.append("}catch(Throwable e){"+ ln);
                        sb.append("throw new UndeclaredThrowableException(e);"+ ln);
                    sb.append("}");
                    sb.append(getReturnEmptyCode(m.getReturnType()));
                sb.append("}");
            }

        sb.append("}" + ln);

        return sb.toString();
    }

    private static Map<Class, Class> mappings =new HashMap();

    static{
        mappings.put(int.class,Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return"return 0;";
        }else if(returnClass == void.class){
            return"";
        }else{
            return"return null;";
        }
    }

    private static String getCaseCode(String code,Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return"(("+ mappings.get(returnClass).getName() +")"+ code +")."+ returnClass.getSimpleName() +"Value()";
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> clazz){
        return clazz != void.class;
    }

    private static String toLowerFirstCase(String src){
        char[] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}

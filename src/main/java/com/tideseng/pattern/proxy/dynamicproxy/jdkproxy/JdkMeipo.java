package com.tideseng.pattern.proxy.dynamicproxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK代理，通过接口实现代理，被代理类需要实现接口
 */
public class JdkMeipo implements InvocationHandler {

    private Object target;

    /**
     * 方式二：实现类
     * 通过字节码重组自动生成新的实现类对象
     *  1、获取目标对象的引用，通过反射获取目标对象的所有接口
     *  2、JDK Proxy类生成一个新的类、并实现目标对象的所有接口
     *  3、动态生成Java代码，把业务逻辑添加到新生成的类中
     *  4、将新生成Java代码生成class文件
     *  5、将class文件重新加载到JVM中运行，得到新生成的代理类并执行
     * @param target
     * @return
     * @throws Exception
     */
    public Object getInstance(Object target) throws Exception{
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * 不同的方法有不同代理时，可以结合策略模式
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(this.target, args);
        after();
        return invoke;
    }

    private void before(){
        System.out.println("佳欢呀，听说你要耍朋友，啥子条件嘛");
    }

    private void after(){
        System.out.println("我懂");
    }

    /**
     * 方式一：匿名内部类
     * @param target
     * @return
     * @throws Exception
     */
    /*public Object getInstance(Object target) throws Exception{
        this.target = target;
        Class<?> clazz = target.getClass();
        Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("佳欢呀，听说你要耍朋友，啥子条件嘛");
                Object invoke = method.invoke(target, args);
                System.out.println(invoke);
                invoke = "我懂";
                return invoke;
            }
        });
        return proxy;
    }*/

}

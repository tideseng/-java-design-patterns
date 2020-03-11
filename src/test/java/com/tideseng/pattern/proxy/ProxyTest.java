package com.tideseng.pattern.proxy;

import com.tideseng.pattern.proxy.customproxy.MyMeipo;
import com.tideseng.pattern.proxy.customproxy.MyProxy;
import com.tideseng.pattern.proxy.dbroute.order.IOrderService;
import com.tideseng.pattern.proxy.dbroute.order.Order;
import com.tideseng.pattern.proxy.dbroute.order.OrderService;
import com.tideseng.pattern.proxy.dbroute.proxy.OrderServiceDynamicProxy;
import com.tideseng.pattern.proxy.dynamicproxy.cglibproxy.CglibMeipo;
import com.tideseng.pattern.proxy.dynamicproxy.cglibproxy.Jiahuan3;
import com.tideseng.pattern.proxy.dynamicproxy.jdkproxy.JdkMeipo;
import com.tideseng.pattern.proxy.dynamicproxy.jdkproxy.Jiahuan2;
import com.tideseng.pattern.proxy.staticproxy.Father;
import com.tideseng.pattern.proxy.staticproxy.Jiahuan;
import com.tideseng.pattern.proxy.staticproxy.Jiahuan1;
import com.tideseng.pattern.proxy.staticproxy.StaticMeipo;
import com.tideseng.pattern.proxy.dbroute.proxy.OrderServiceStaticProxy;
import net.sf.cglib.core.DebuggingClassWriter;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProxyTest {

    /**
     * 静态代理，只能给指定的类进行代理
     */
    @Test
    public void staticProxy1(){
        Father father = new Father(new Jiahuan());
        String result = father.findFriend();
        System.out.println(result);
        System.out.println(father.getClass());
    }

    /**
     * 静态代理，可以给指定接口的所有实现类进行代理
     */
    @Test
    public void staticProxy2(){
        StaticMeipo father = new StaticMeipo(new Jiahuan1());
        String result = father.findFriend();
        System.out.println(result);
        System.out.println(father.getClass());
    }

    /**
     * 通过静态代理分配数据源
     * @throws Exception
     */
    @Test
    public void dbRouteStaticProxy() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse("2020/02/29");
        Order order = new Order();
        order.setCreateTime(date.getTime());
        // 返回的是静态代理对象实例
        IOrderService orderService = new OrderServiceStaticProxy(new OrderService());
        orderService.createOrder(order); // 静态代理对象执行方法
    }

    @Test
    public void dbRouteDynamicProxy() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
        Date date = sdf.parse("2020/02/29");
        Order order = new Order();
        order.setCreateTime(date.getTime());
        // 返回的是JDK动态代理动态生成的新对象实例
        IOrderService orderService = (IOrderService) new OrderServiceDynamicProxy().getInstance(new OrderService());
        orderService.createOrder(order); // 动态代理对象执行方法
    }

    /**
     * JDK代理，返回的实例是JDK动态生成的对象，实现了目标对象的接口
     * 当调用方法时，实际上是调用InvocationHandler实现类（即代理类）的invoke方法
     * @throws Exception
     */
    @Test
    public void jdkProxy() throws Exception {
        Person jiahuan = (Person) new JdkMeipo().getInstance(new Jiahuan2());
        String result = jiahuan.findFriend();
        System.out.println(result);
        System.out.println(jiahuan.getClass()); // com.sun.proxy.$Proxy4（$开头的类一般是自动生成）
    }

    @Test
    public void jdkProxy1() throws Exception {
        Object obj = new JdkMeipo().getInstance(new Jiahuan2());
        Object result = obj.getClass().getMethod("findFriend", null).invoke(obj);
        System.out.println(result);
        System.out.println(obj.getClass()); // com.sun.proxy.$Proxy4（$开头的类一般是自动生成）
    }

    /**
     * 将JDK代理生成的代理对象保存到本地（代理对象class的名称要与上面打印的对象名保持一致）
     *  通过反编译从内存中获取JDK代理的字节码重组生成的代理类源代码（把class文件拖到idea即可）
     * @throws Exception
     */
    @Test
    public void saveJdkProxy() throws Exception {
        jdkProxy();

        byte [] bytes = ProxyGenerator.generateProxyClass("$Proxy4", new Class[]{Person.class});
        FileOutputStream os = new FileOutputStream(
                ProxyTest.class.getResource("").toURI().getPath() + "/$Proxy4.class");
        os.write(bytes);
        os.close();
    }

    @Test
    public void customProxy() throws Exception{
        Person jiahuan = (Person) new MyMeipo().getInstance(new Jiahuan2());
        String result = jiahuan.findFriend();
        System.out.println(result);
        System.out.println(jiahuan.getClass());
    }

    @Test
    public void cglibProxy() throws Exception {
        Jiahuan3 jiahuan = (Jiahuan3) new CglibMeipo().getInstance(Jiahuan3.class);
        String result = jiahuan.findFriend();
        System.out.println(result);
        System.out.println(jiahuan.getClass()); // com.tideseng.pattern.proxy.dynamicproxy.cglibproxy.Jiahuan3$$EnhancerByCGLIB$$14bd26f9
    }

    /**
     * 将CGLIB代理生成的代理对象保存到本地
     *  会有3个class文件
     * @throws Exception
     */
    @Test
    public void saveCglibProxy() throws Exception {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,
                ProxyTest.class.getResource("").toURI().getPath() + "/cglib_proxy_class");
        cglibProxy();
    }

}

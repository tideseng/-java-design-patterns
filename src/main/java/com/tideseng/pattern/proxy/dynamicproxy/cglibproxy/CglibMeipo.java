package com.tideseng.pattern.proxy.dynamicproxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB代理，通过继承实现代理，对被代理类没有要求
 */
public class CglibMeipo implements MethodInterceptor {

    private Object target;

    /**
     * 方式一：接收字节码对象，拦截方法中需要使用代理方法进行调用
     * @param clazz
     * @return
     */
    public Object getInstance(Class<?> clazz) {
        // Enhancer相当于JDK的Proxy代理工具类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz); // 指定需要继承的类
        enhancer.setCallback(this); // 指定回调对象
        return enhancer.create();
    }

    /**
     * 方式二：接收对象实例，拦截方法中可用代理方法和对象方法进行调用
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        return Enhancer.create(target.getClass(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("佳欢呀，听说你要耍朋友，啥子条件嘛");
        Object invoke = methodProxy.invokeSuper(o, objects); // 方式一和方式二可用
//        Object invoke = methodProxy.invokeSuper(this.target, objects); // 方式二可用
        System.out.println(invoke);
        invoke = "我懂";
        return invoke;
    }

    /**
     * 方式三：使用字节码对象匿名内部类
     * @param clazz
     * @return
     */
    public Object getInstance3(Class<?> clazz) {
        return Enhancer.create(clazz, clazz.getInterfaces(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("佳欢呀，听说你要耍朋友，啥子条件嘛");
                Object invoke = methodProxy.invokeSuper(o, objects); // 使用代理方法
                System.out.println(invoke);
                invoke = "我懂";
                return invoke;
            }
        });
    }

    /**
     * 方式四：使用实例对象的匿名内部类
     * @param target
     * @return
     */
    public Object getInstance4(Object target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return Enhancer.create(clazz, target.getClass().getInterfaces(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("佳欢呀，听说你要耍朋友，啥子条件嘛");
//                Object invoke = methodProxy.invokeSuper(o, objects); // 使用代理方法
                Object invoke = method.invoke(target, objects); // 使用对象方法
                System.out.println(invoke);
                invoke = "我懂";
                return invoke;
            }
        });
    }

    public static void main(String[] args) {
        Jiahuan3 jiahuan = (Jiahuan3) new CglibMeipo().getInstance3(Jiahuan3.class);
//        Jiahuan3 jiahuan = (Jiahuan3) new CglibMeipo().getInstance4(new Jiahuan3());
        String result = jiahuan.findFriend();
        System.out.println(result);
        System.out.println(jiahuan.getClass());
    }

}

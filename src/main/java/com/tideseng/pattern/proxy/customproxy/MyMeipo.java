package com.tideseng.pattern.proxy.customproxy;

import java.lang.reflect.Method;

/**
 *
 */
//public class MyMeipo implements InvocationHandler {
public class MyMeipo implements MyInvocationHandler {

    private Object target;

    public Object getInstance(Object target) throws Exception{
        this.target = target;
        Class<?> clazz = target.getClass();
//        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
        return MyProxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
    }

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


}

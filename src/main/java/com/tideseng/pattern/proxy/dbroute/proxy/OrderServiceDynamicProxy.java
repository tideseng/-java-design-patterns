package com.tideseng.pattern.proxy.dbroute.proxy;

import com.tideseng.pattern.proxy.dbroute.db.DynamicDataSourceEntity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 动态代理数据源切换的逻辑
 */
public class OrderServiceDynamicProxy implements InvocationHandler {

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    /**
     * 动态代理可以代理任意对象
     */
    private Object proxyobj;

    public Object getInstance(Object proxyobj){
        this.proxyobj = proxyobj;
        Class<?> clazz = proxyobj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    //args是参数列表，可以获取被代理对象的参数

    /**
     * 调用动态代理生成新对象的接口时，就会执行该方法
     * @param proxy 新生成的代理对象
     * @param method 代理对象要执行的方法
     * @param args 代理对象要执行方法的参数列表
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        before(args[0]);
        Object object = method.invoke(proxyobj, args);
        after();
        return object;
    }

    private void before(Object target){
        try{
            // 进行数据源的切换
            System.out.println("Proxy before method");

            // 约定优于配置，即接口和实现类必须要有"getCreateTime"方法
            Long time = (Long) target.getClass().getMethod("getCreateTime").invoke(target);
            Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
            System.out.println("动态代理类自动分配到【DB_" + dbRouter + "】数据源处理数据");
            DynamicDataSourceEntity.set(dbRouter);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void after(){
        System.out.println("Proxy after method");
        DynamicDataSourceEntity.restore(); // 还原成默认的数据源
    }

}
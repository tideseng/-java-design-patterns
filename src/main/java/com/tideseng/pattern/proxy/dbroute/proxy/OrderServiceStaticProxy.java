package com.tideseng.pattern.proxy.dbroute.proxy;

import com.tideseng.pattern.proxy.dbroute.db.DynamicDataSourceEntity;
import com.tideseng.pattern.proxy.dbroute.order.IOrderService;
import com.tideseng.pattern.proxy.dbroute.order.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 静态代理数据源切换的逻辑
 * 实现代理对象接口的目的是：
 *      代理类拿到被代理引用
 *      调用的仍是被代理对象方法
 *      返回的也是被代理对象方法的返回值
 *      只是调用方从被代理者变成了代理者而已
 */
public class OrderServiceStaticProxy implements IOrderService {

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    /**
     * 静态代理只能代理指定的对象
     */
    private IOrderService orderService;

    public OrderServiceStaticProxy(IOrderService orderService){
        this.orderService = orderService;
    }

    public int createOrder(Order order){
        Long time = order.getCreateTime();
        Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
        System.out.println("静态代理类自动分配到【DB_" +  dbRouter + "】数据源处理数据");
        DynamicDataSourceEntity.set(dbRouter);

        int result = this.orderService.createOrder(order); // 还原成默认的数据源

        DynamicDataSourceEntity.restore();
        return result;
    }

}

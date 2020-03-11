package com.tideseng.pattern.proxy.dbroute.order;

public class OrderService implements IOrderService {

    // 静态代理的标志，显式声明被代理对象
    private OrderDao orderDao;

    /**
     * 为了方便在构造方法中直接将OrderDao初始化，spring是自动注入
     */
    public OrderService(){
        orderDao = new OrderDao();
    }

    @Override
    public int createOrder(Order order) {
        System.out.println("OrderService调用orderDao创建订单");
        return orderDao.insert(order);
    }

}
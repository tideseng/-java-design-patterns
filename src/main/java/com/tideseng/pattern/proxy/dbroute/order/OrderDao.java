package com.tideseng.pattern.proxy.dbroute.order;

public class OrderDao {

    public int insert(Order order){
        System.out.println("OrderDao创建order成功");
        return 1;
    }

}

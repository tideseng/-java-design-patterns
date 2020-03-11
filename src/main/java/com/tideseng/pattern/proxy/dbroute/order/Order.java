package com.tideseng.pattern.proxy.dbroute.order;

import lombok.Data;

@Data
public class Order {

    private String id;
    private Object orderinfo;
    private long createTime; // 订单创建时间按年分库

}

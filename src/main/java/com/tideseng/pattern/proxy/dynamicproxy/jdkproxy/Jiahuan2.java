package com.tideseng.pattern.proxy.dynamicproxy.jdkproxy;

import com.tideseng.pattern.proxy.Person;

/**
 * 目标对象需要实现接口
 */
public class Jiahuan2 implements Person {

    @Override
    public String findFriend() {
        System.out.println("我要细腰滴");
        return "嘿嘿嘿";
    }

}

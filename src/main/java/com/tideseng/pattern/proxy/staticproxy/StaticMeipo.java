package com.tideseng.pattern.proxy.staticproxy;

import com.tideseng.pattern.proxy.Person;

/**
 * 老爸可以给所有人找对象
 */
public class StaticMeipo {

    private Person target;

    public StaticMeipo(Person target){
        this.target = target;
    }

    public String findFriend(){
        System.out.println("佳欢呀，听说你要耍朋友，啥子条件嘛");
        String invoke = this.target.findFriend();
        System.out.println(invoke);
        invoke = "我懂";
        return invoke;
    }

}

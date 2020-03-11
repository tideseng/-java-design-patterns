package com.tideseng.pattern.proxy.staticproxy;

/**
 * 老爸只能给指定的儿子找对象
 */
public class Father {

    private Jiahuan target;

    public Father(Jiahuan target){
        this.target = target;
    }

    public String findFriend(){
        System.out.println("兔崽子呀，听说你要耍朋友，啥子条件嘛");
        String invoke = this.target.findFriend();
        System.out.println(invoke);
        invoke = "我懂";
        return invoke;
    }

}

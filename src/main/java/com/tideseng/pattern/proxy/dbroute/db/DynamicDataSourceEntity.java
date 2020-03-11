package com.tideseng.pattern.proxy.dbroute.db;

/**
 * 动态切换数据源
 */
public class DynamicDataSourceEntity {

    // 默认数据源
    public final static String DEFAULE_SOURCE = null;

    private final static ThreadLocal<String> local = new ThreadLocal<String>();

    // 构造方法私有化（单例模式）
    private DynamicDataSourceEntity(){}

    // 设置已知名字的数据源，如：DB_2019、DB_2020
    public static void set(String source){
        local.set(source);
    }

    // 根据名字动态设置数据源
    public static void set(int year){
        local.set("DB_" + year);
    }

    // 获取当前正在使用的数据源名字
    public static String get(){
        return local.get();
    }

    // 还原当前切面的数据源
    public static void restore(){
        local.set(DEFAULE_SOURCE);
    }

    // 清空数据源
    public static void clear(){
        local.remove();
    }

}

package com.tideseng.pattern.factory.method;

import com.tideseng.pattern.factory.ICourse;

/**
 * 工厂方法模式：让接口实现类指定生产哪种产品
 *  满足单一职责原则，指定的工厂生产指定的产品
 *  只满足单一产品
 */
public interface ICourseFactory {

    /**
     * 不需要传入参数
     * @return
     */
    ICourse create();

}

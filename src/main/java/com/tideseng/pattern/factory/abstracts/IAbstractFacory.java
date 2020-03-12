package com.tideseng.pattern.factory.abstracts;

import com.tideseng.pattern.factory.ICourse;
import com.tideseng.pattern.factory.INote;

/**
 * 抽象工厂模式：让接口实现类指定产品族的所有产品，强调一系列相关产品一起创建
 *  抽象工厂中的产品可以选择
 *  不满足开闭原则（适用迭代速度慢）
 */
public interface IAbstractFacory {

    /**
     * 不需要传入参数
     * @return
     */
    ICourse createCourse();

    /**
     * 不需要传入参数
     * @return
     */
    INote createNote();

}

package com.tideseng.pattern.factory.simple;

import com.tideseng.pattern.factory.ICourse;
import com.tideseng.pattern.factory.JavaCourse;
import com.tideseng.pattern.factory.PythonCourse;

/**
 * 简单工厂模式：由工厂对象决定创建哪种产品
 *  工厂类职责相对过重，所有产品的创建逻辑都在一起，不满足开闭原则
 */
public class CourseFactory {

    /**
     * 需要传入正确的参数
     * 新增产品时需要修改创建逻辑
     * @param flag
     * @return
     */
    public ICourse create(Integer flag){
        if(flag != null) {
            if (flag.equals(1)) {
                return new JavaCourse();
            } else if (flag.equals(2)) {
                return new PythonCourse();
            }
        }
        return null;
    }

    /**
     * 需要传入正确的参数
     * 新增产品时需要修改创建逻辑
     * @param className
     * @return
     */
    public ICourse create(String className){
        if(className != null) {
            try {
                return (ICourse) Class.forName(className).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 需要传入正确的参数
     * 新增产品时可能需要修改创建逻辑
     * 只能创建一个产品，不能扩展复杂的产品结构（产品族）
     * @param clazz
     * @return
     */
    public ICourse create(Class clazz){
        if(clazz != null){
            try {
                return (ICourse) clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

package com.tideseng.pattern.factory.method;

import com.tideseng.pattern.factory.ICourse;
import com.tideseng.pattern.factory.JavaCourse;

public class JavaCourseFactory implements ICourseFactory {

    public ICourse create() {
        return new JavaCourse();
    }

}

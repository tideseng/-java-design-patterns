package com.tideseng.pattern.factory.method;

import com.tideseng.pattern.factory.ICourse;
import com.tideseng.pattern.factory.PythonCourse;

public class PythonCourseFactory implements ICourseFactory {

    public ICourse create() {
        return new PythonCourse();
    }

}

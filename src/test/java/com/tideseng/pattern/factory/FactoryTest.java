package com.tideseng.pattern.factory;

import com.tideseng.pattern.factory.abstracts.JavaFactory;
import com.tideseng.pattern.factory.method.JavaCourseFactory;
import com.tideseng.pattern.factory.simple.CourseFactory;
import org.junit.Test;

public class FactoryTest {

    @Test
    public void simpleFactory1() {
        ICourse java = new CourseFactory().create(1);
        java.detail();
    }

    @Test
    public void simpleFactory2() {
        ICourse java = new CourseFactory().create("com.tideseng.pattern.factory.PythonCourse");
        java.detail();
    }

    @Test
    public void simpleFactory3() {
        ICourse java = new CourseFactory().create(PythonCourse.class);
        java.detail();
    }

    @Test
    public void factoryMethod() {
        ICourse java = new JavaCourseFactory().create();
        java.detail();
    }

    @Test
    public void abstractFactory() {
        JavaFactory javaFactory = new JavaFactory();
        javaFactory.createCourse().detail();
        javaFactory.createNote().detail();
    }

}

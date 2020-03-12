package com.tideseng.pattern.factory.abstracts;

import com.tideseng.pattern.factory.ICourse;
import com.tideseng.pattern.factory.INote;
import com.tideseng.pattern.factory.JavaCourse;
import com.tideseng.pattern.factory.JavaNote;

public class JavaFactory implements IAbstractFacory {

    public ICourse createCourse() {
        return new JavaCourse();
    }

    public INote createNote() {
        return new JavaNote();
    }

}

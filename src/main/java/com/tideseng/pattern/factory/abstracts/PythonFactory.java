package com.tideseng.pattern.factory.abstracts;

import com.tideseng.pattern.factory.ICourse;
import com.tideseng.pattern.factory.INote;
import com.tideseng.pattern.factory.PythonCourse;
import com.tideseng.pattern.factory.PythonNote;

public class PythonFactory implements IAbstractFacory {

    public ICourse createCourse() {
        return new PythonCourse();
    }

    public INote createNote() {
        return new PythonNote();
    }

}

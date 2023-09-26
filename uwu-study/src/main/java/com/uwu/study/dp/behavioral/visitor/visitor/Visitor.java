package com.uwu.study.dp.behavioral.visitor.visitor;


import com.uwu.study.dp.behavioral.visitor.person.Student;
import com.uwu.study.dp.behavioral.visitor.person.Teacher;

public interface Visitor {
    public void visitStudent(Student student);
    public void visitTeacher(Teacher student);
}

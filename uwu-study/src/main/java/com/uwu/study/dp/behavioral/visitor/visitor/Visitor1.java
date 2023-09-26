package com.uwu.study.dp.behavioral.visitor.visitor;


import com.uwu.study.dp.behavioral.visitor.person.Student;
import com.uwu.study.dp.behavioral.visitor.person.Teacher;

public class Visitor1 implements Visitor{

    private int studentsAgeSum = 0;
    private int teacherAgeSum = 0;

    public int getStudentsAgeSum() {
        return studentsAgeSum;
    }

    public int getTeacherAgeSum() {
        return teacherAgeSum;
    }

    @Override
    public void visitStudent(Student student) {
        System.out.println("访问者1访问学生："+student.getName()+" 年龄："+student.getAge());
        studentsAgeSum += student.getAge();
    }

    @Override
    public void visitTeacher(Teacher teacher) {
        System.out.println("访问者1访问学生："+teacher.getName()+" 年龄："+teacher.getAge());
        teacherAgeSum += teacher.getAge();
    }
}

package com.uwu.study.dp.behavioral.visitor.visitor;


import com.uwu.study.dp.behavioral.visitor.person.Student;
import com.uwu.study.dp.behavioral.visitor.person.Teacher;

public class Visitor2 implements Visitor{
    private int maxScore = -1;
    private int maxWorkYear = -1;

    public int getMaxScore() {
        return maxScore;
    }

    public int getMaxWorkYear() {
        return maxWorkYear;
    }

    @Override
    public void visitStudent(Student student) {
        System.out.println("访问者2访问学生："+student.getName()+" 成绩："+student.getScore());
        maxScore = Math.max(maxScore, student.getScore());
    }

    @Override
    public void visitTeacher(Teacher teacher) {
        System.out.println("访问者2访问老师："+teacher.getName()+" 工龄："+teacher.getWorkYear());
        maxWorkYear = Math.max(maxWorkYear,teacher.getWorkYear());
    }
}

package com.uwu.study.dp.behavioral.visitor.person;


import com.uwu.study.dp.behavioral.visitor.visitor.Visitor;

public class Teacher extends Person{

    private int workYear;

    public Teacher(String name,int age,int workYear){
        super(name, age);
        this.workYear = workYear;
    }
    @Override
    public void Accept(Visitor visitor) {
        visitor.visitTeacher(this);
    }

    public int getWorkYear() {
        return workYear;
    }
}

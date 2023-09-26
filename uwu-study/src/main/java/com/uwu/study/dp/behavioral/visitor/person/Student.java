package com.uwu.study.dp.behavioral.visitor.person;


import com.uwu.study.dp.behavioral.visitor.visitor.Visitor;

public class Student extends Person{

    private int score;

    public Student(String name,int age,int score){
        super(name,age);
        this.score = score;
    }
    @Override
    public void Accept(Visitor visitor) {
        visitor.visitStudent(this);
    }

    public int getScore() {
        return score;
    }
}

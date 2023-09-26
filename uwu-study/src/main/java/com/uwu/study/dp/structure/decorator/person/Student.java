package com.uwu.study.dp.structure.decorator.person;

public class Student extends Person {
    public Student(String name){
        this.name = name;
    }
    @Override
    public void operation() {
        System.out.println(name + "的职责是：学习");
    }
}

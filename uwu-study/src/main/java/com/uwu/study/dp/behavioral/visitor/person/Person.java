package com.uwu.study.dp.behavioral.visitor.person;


import com.uwu.study.dp.behavioral.visitor.visitor.Visitor;

public abstract class Person {
    private String name;
    private int age;

    public Person(String name,int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public abstract void Accept(Visitor visitor);
}

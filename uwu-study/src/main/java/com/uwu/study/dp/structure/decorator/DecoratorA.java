package com.uwu.study.dp.structure.decorator;


import com.uwu.study.dp.structure.decorator.person.Person;

public class DecoratorA extends Decorator{
    public DecoratorA(Person person){
        this.person = person;
    }

    @Override
    public void operation() {
        person.operation();
        System.out.println("写作业");
    }
}

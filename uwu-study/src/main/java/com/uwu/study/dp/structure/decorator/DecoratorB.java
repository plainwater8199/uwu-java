package com.uwu.study.dp.structure.decorator;


import com.uwu.study.dp.structure.decorator.person.Person;

public class DecoratorB extends Decorator{

    public DecoratorB(Person person){
        this.person = person;
    }
    @Override
    public void operation() {
        person.operation();
        System.out.println("考试");
    }
}

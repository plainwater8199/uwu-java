package com.uwu.study.dp.behavioral.visitor.person;


import com.uwu.study.dp.behavioral.visitor.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class PersonStructure {
    private List<Person> personList = new ArrayList<>();

    public PersonStructure(){
        personList.add(new Student("张三",20,70));
        personList.add(new Student("李四",18,80));
        personList.add(new Student("王五",22,99));


        personList.add(new Teacher("李老师",35,10));
        personList.add(new Teacher("汪老师",40,12));
        personList.add(new Teacher("刘老师",42,16));
    }

    public void accept(Visitor visitor){
        for(Person person : personList){
            person.Accept(visitor);
        }
    }
}

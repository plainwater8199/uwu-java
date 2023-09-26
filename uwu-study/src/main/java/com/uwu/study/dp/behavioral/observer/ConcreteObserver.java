package com.uwu.study.dp.behavioral.observer;

public class ConcreteObserver implements Observer{
    private String name;

    private String state;

    private Subject subject;

    public ConcreteObserver(String name,Subject subject){
        this.name = name;
        this.subject = subject;
        subject.attach(this);
        state = subject.getState();
    }
    @Override
    public void update() {
        System.out.println(name+"  收到通知");
        state = subject.getState();
        System.out.println(name+ " 改变后的状态为： "+ state);
    }
}

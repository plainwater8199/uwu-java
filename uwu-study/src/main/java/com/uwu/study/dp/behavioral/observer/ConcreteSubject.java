package com.uwu.study.dp.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject{
    private String name;
    private String state;

    private List<Observer> observers;

    public ConcreteSubject(String name){
        state = "未更新";
        this.name = name;
        observers = new ArrayList<>();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for(Observer observer : observers){
            observer.update();
        }
    }

    @Override
    public void setState(String state) {
        this.state = state;
        System.out.println(name + "的状态发生类变化，变化后的状态为："+state);
        notifyObserver();
    }

    @Override
    public String getState() {
        return state;
    }
}

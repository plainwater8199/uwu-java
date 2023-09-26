package com.uwu.study.dp.behavioral.observer;

public interface Subject {
    //添加观察者
    public void attach(Observer observer);
    //删除观察者
    public void detach(Observer observer);
    //状态改变后，通知所有观察者
    public void notifyObserver();
    //设置状态
    public void setState(String state);
    //获取状态
    public String getState();
}

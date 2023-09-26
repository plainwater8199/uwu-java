package com.uwu.study.dp.behavioral.mediator.colleague;


import com.uwu.study.dp.behavioral.mediator.mediator.Mediator;

public class Colleague2 extends Colleague{
    public Colleague2(Mediator mediator){
        this.mediator = mediator;
    }
    public void sendMessage(String message){
        mediator.sendMessage(message,this);
    }
    public void notify(String message){
        System.out.println("同事2收到消息："+message);
    }
}

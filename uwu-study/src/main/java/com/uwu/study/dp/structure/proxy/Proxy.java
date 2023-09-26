package com.uwu.study.dp.structure.proxy;

public class Proxy implements Subject{
    protected RealSubject realSubject;

    public Proxy(RealSubject realSubject){
        this.realSubject = realSubject;
    }
    @Override
    public void buy() {
        System.out.println("办理购买前的手续");
        realSubject.buy();
        System.out.println("办理购买后的手续");
    }
}

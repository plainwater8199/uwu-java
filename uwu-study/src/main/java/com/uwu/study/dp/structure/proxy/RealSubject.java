package com.uwu.study.dp.structure.proxy;

public class RealSubject implements Subject {
    @Override
    public void buy() {
        System.out.println("付钱");
    }
}

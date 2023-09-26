package com.uwu.study.dp.strategy.strategy2;

public class Context {

    PayStrategy strategy;

    public  Context(PayStrategy strategy){
        this.strategy = strategy;
    }

    public void exec(){

        strategy.beforeExec();
        strategy.Exec();
        strategy.afterExec();
    }
}

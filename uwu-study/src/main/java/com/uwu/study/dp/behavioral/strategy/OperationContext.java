package com.uwu.study.dp.behavioral.strategy;


import com.uwu.study.dp.behavioral.strategy.strategy.Strategy;

public class OperationContext {
    private Strategy strategy;

    public OperationContext(Strategy strategy){
        this.strategy = strategy;
    }

    public void Operation(int a ,int b){
        strategy.TwoNumberOperation(a,b);
    }
}

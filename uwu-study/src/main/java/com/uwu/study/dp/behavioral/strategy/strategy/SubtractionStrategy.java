package com.uwu.study.dp.behavioral.strategy.strategy;

public class SubtractionStrategy implements Strategy{
    @Override
    public void TwoNumberOperation(int a, int b) {
        System.out.println(a-b);
    }
}

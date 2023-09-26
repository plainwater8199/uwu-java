package com.uwu.study.dp.behavioral.strategy.strategy;

public class MultiplyStrategy implements Strategy{
    @Override
    public void TwoNumberOperation(int a, int b) {
        System.out.println(a * b);
    }
}

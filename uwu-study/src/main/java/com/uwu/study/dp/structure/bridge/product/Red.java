package com.uwu.study.dp.structure.bridge.product;

public class Red implements Color{
    @Override
    public void OperationImp(String name) {
        System.out.println(name + ":红色");
    }
}

package com.uwu.study.dp.structure.bridge.product;

public class ProductA extends Product{

    @Override
    public void Operation() {
        color.OperationImp(this.getName());
    }
}

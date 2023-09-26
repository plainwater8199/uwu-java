package com.uwu.study.dp.create.factory;


public class FactoryA implements Factory{
    @Override
    public Product createProduct() {
        return new ProductA();
    }
}

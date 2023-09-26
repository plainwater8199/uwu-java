package com.uwu.study.dp.create.factory;

public class FactoryB implements Factory{
    @Override
    public Product createProduct() {
        return new ProductB();
    }
}

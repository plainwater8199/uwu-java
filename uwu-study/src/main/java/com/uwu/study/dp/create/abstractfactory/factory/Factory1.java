package com.uwu.study.dp.create.abstractfactory.factory;


import com.uwu.study.dp.create.abstractfactory.product.ProductA;
import com.uwu.study.dp.create.abstractfactory.product.ProductA1;
import com.uwu.study.dp.create.abstractfactory.product.ProductB;
import com.uwu.study.dp.create.abstractfactory.product.ProductB1;

public class Factory1 implements Factory{
    @Override
    public ProductA createProductA() {
        return new ProductA1();
    }

    @Override
    public ProductB createProductB() {
        return new ProductB1();
    }
}

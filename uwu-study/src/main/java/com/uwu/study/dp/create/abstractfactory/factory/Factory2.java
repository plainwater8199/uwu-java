package com.uwu.study.dp.create.abstractfactory.factory;


import com.uwu.study.dp.create.abstractfactory.product.ProductA;
import com.uwu.study.dp.create.abstractfactory.product.ProductA2;
import com.uwu.study.dp.create.abstractfactory.product.ProductB;
import com.uwu.study.dp.create.abstractfactory.product.ProductB2;

public class Factory2 implements Factory{
    @Override
    public ProductA createProductA() {
        return new ProductA2();
    }

    @Override
    public ProductB createProductB() {
        return new ProductB2();
    }
}

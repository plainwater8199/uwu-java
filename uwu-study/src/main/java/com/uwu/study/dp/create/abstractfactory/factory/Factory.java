package com.uwu.study.dp.create.abstractfactory.factory;


import com.uwu.study.dp.create.abstractfactory.product.ProductA;
import com.uwu.study.dp.create.abstractfactory.product.ProductB;

public interface Factory {

    public ProductA createProductA();
    public ProductB createProductB();
}

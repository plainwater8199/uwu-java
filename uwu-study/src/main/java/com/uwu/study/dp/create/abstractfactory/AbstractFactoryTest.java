package com.uwu.study.dp.create.abstractfactory;


import com.uwu.study.dp.create.abstractfactory.factory.Factory;
import com.uwu.study.dp.create.abstractfactory.factory.Factory1;
import com.uwu.study.dp.create.abstractfactory.factory.Factory2;
import com.uwu.study.dp.create.abstractfactory.product.ProductA;
import com.uwu.study.dp.create.abstractfactory.product.ProductB;

public class AbstractFactoryTest {
    public static void main(String[] args) {
        Factory factory1 = new Factory1();
        ProductA productA = factory1.createProductA();
        productA.info();

        Factory factory2 = new Factory2();
        ProductB productB = factory2.createProductB();
        productB.info();
    }
}

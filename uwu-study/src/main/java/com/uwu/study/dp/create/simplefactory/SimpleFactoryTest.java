package com.uwu.study.dp.create.simplefactory;

public class SimpleFactoryTest {
    public static void main(String[] args) {
        Product productA = SimpleFactory.createProduct("A");
        if(productA != null){
            productA.info();
        }

        Product productB = SimpleFactory.createProduct("B");
        if(productB != null){
            productB.info();
        }

        Product productC = SimpleFactory.createProduct("C");
        if(productC != null){
            productC.info();
        }


    }
}

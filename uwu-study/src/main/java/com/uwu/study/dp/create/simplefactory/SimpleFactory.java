package com.uwu.study.dp.create.simplefactory;

public class SimpleFactory {
    public static Product createProduct(String type){
        Product product = null;

        switch (type){
            case "A":
                product = new ProductA();
                break;
            case "B":
                product = new ProductB();
                break;
            default:
                System.out.println("没有该产品");
                break;
        }
        return product;
    }

}

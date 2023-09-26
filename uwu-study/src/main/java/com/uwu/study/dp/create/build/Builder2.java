package com.uwu.study.dp.create.build;

public class Builder2 extends Builder{
    Product product = new Product();

    @Override
    public void buildPart() {
        product.add("1");
        product.add("2");
        product.add("3");
        product.add("4");

    }

    @Override
    public Product getResult() {
        return product;
    }
}

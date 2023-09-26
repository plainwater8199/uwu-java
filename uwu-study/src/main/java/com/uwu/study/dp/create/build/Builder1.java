package com.uwu.study.dp.create.build;

public class Builder1 extends Builder{
    Product product = new Product();

    @Override
    public void buildPart() {
        product.add("A");
        product.add("B");
        product.add("C");
        product.add("D");
        product.add("E");
        product.add("F");

    }

    @Override
    public Product getResult() {
        return product;
    }
}

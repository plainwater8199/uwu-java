package com.uwu.study.dp.structure.bridge.product;



public abstract class Product {
    private String name;
    protected Color color;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void Operation();
}

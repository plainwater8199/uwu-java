package com.uwu.study.dp.structure.flyweight;

public class Circle extends Shape{

    public Circle(String color){
        this.color = color;
    }
    @Override
    public void draw(int x, int y) {
        System.out.println("draw a color:"+color+"   circle x:"+x+" y:"+y);
    }
}

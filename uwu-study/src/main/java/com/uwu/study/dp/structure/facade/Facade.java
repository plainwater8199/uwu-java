package com.uwu.study.dp.structure.facade;


/**
 * 为系统对外提供统一的入口，可以对客户端隐藏子系统内部实现的细节，也降低类客户端与子系统之间的耦合度。
 */
public class Facade {
    protected SubSystemOne subSystemOne;
    protected SubSystemTwo subSystemTwo;
    protected SubSystemThree subSystemThree;

    public Facade(){
        subSystemOne = new SubSystemOne();
        subSystemTwo = new SubSystemTwo();
        subSystemThree = new SubSystemThree();
    }

    public void methodA(){
        subSystemOne.methodOne();
    }

    public void methodB(){
        subSystemTwo.methodTwo();
    }

    public void methodC(){
        subSystemThree.methodThree();
    }
}

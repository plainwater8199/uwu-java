package com.uwu.study.dp.structure.facade;

public class FacadePatternTest {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.methodA();
        facade.subSystemOne.methodOne();
        facade.methodB();
        facade.methodC();

    }
}

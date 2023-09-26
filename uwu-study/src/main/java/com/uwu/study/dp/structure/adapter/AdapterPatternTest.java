package com.uwu.study.dp.structure.adapter;


/**
 * 适配器模式(Adapter Pattern)指的是把一个类的接口变换成客户端所期待的另一种接口，从而使原本接口不匹配而无法在一起工作的两个类能够在一起工作。
 * 适配器模式中的角色：请求者（client）、目标角色（Target）、源角色（Adaptee）、适配器角色（Adapter），这四个角色是保证这个设计模式运行的关键。
 *
 * client：需要使用适配器的对象，不需要关心适配器内部的实现，只对接目标角色。
 * Target：目标角色，和client直接对接，定义了client需要用到的功能。
 * Adaptee：需要被进行适配的对象。
 * Adapter：适配器，负责将源对象转化，给client做适配。
 */
public class AdapterPatternTest {
    public static void main(String[] args) {
        USB usb = new Adapter();
        usb.Request();
    }
}

package com.uwu.study.dp.structure.decorator;


import com.uwu.study.dp.structure.decorator.person.Person;
import com.uwu.study.dp.structure.decorator.person.Student;

/**
 * 指在不改变现有对象结构的情况下，动态的给该对象增加一些职责（即增加器额外功能）的模式，属于对象结构型模型
 * 优点：
 * 1、采用装饰模式扩展对象的功能比采用继承方式更加灵活
 * 2、可以设计出多个不同的具体装饰类，创造出多个不同行为的组合。
 *
 * 缺点：
 *装饰模式增加类很多子类，如果过度使用是程序变的很复杂。
 */
public class DecoratorPatternTest {
    public static void main(String[] args) {
        Person zhangsan = new Student("张三");
        zhangsan = new DecoratorA(zhangsan);

//        zhangsan = new DecoratorB(zhangsan);
        zhangsan.operation();
        System.out.println("====================");

        Person lisi = new DecoratorB(new DecoratorA(new Student("李四")));
        lisi.operation();
    }
}

package com.uwu.study.dp.create.build;


/**
 * 创建者模式：时将一个复杂的对象的构建与它的表示分离，是的同样的构建过程可以创建不同的方式进行创建。
 *  工厂类模式时提供的创建单个类的产品
 *  而创建者模式则是将各种产品集中起来进行管理，用来具有不同的属性的产品
 */
public class BuilderTest {
    public static void main(String[] args) {
        Director director = new Director();

        Builder builder1 = new Builder1();
        director.Construct(builder1);
        Product product1 = builder1.getResult();
        product1.show();


        Builder builder2 = new Builder2();
        director.Construct(builder2);
        Product product2 = builder2.getResult();
        product2.show();
    }
}

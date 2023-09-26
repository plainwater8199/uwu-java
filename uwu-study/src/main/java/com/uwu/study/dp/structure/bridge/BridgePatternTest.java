package com.uwu.study.dp.structure.bridge;


import com.uwu.study.dp.structure.bridge.product.*;

/**
 * 1、实现了抽象和实现部分的分离，从而极大的提供了系统的灵活性，让抽象部分和实现部分独立开来，这有助于系统进行分层设计，从而产生更好的结构化系统。
 * 2、对于系统的高层部分，只需要知道抽象部分和实现部分的接口就可以了，其它的部分由具体业务来完成。
 * 3、桥接模式替代多层继承方案，可以减少子类的个数，降低系统的管理和维护成本。
 * 4、桥接模式的引入增加了系统的理解和设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计和编程
 * 5、桥接模式要求正确识别出系统中两个独立变化的维度，因此其使用范围有一定的局限性，即需要有这样的应用场景。使用 桥接模式 改进传统方式，让程序具有搞好的扩展性，利用程序维护。

 */
public class BridgePatternTest {
    public static void main(String[] args) {
        Product productA1 = new ProductA();
        Product productA2 = new ProductA();

        Color red = new Red();
        productA1.setName("产品A1");
        productA1.setColor(red);
        productA1.Operation();


        Color blue = new Blue();
        productA2.setName("产品A2");
        productA2.setColor(blue);
        productA2.Operation();
    }
}

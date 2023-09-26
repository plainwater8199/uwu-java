package com.uwu.study.dp.structure.flyweight;

import java.util.Random;


/**
 * 运用共享技术来有效地支持大量细粒度对象的复用。它通过共享已经存在的对象来大幅度减少需要创建的对象数量、避免大量相似类的开销，从而提高系统资源的利用率。
 * 享元模式的主要优点是：相同对象只要保存一份，这降低了系统中对象的数量，从而降低了系统中细粒度对象给内存带来的压力。
 *
 * 其主要缺点是：
 * 1、为了使对象可以共享，需要将一些不能共享的状态外部化，这将增加程序的复杂性。
 * 2、读取享元模式的外部状态会使得运行时间稍微变长。
 */
public class FlyWeightPatternTest {
    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();

        Random random = new Random();
        String[] colors = {"red","blue","green","white","black"};
        for(int i = 1; i<= 100; i++){
            int x = random.nextInt(colors.length);
            Shape shape = factory.getShape(colors[x]);

            System.out.print("第"+i+ "个圆： ");
            shape.draw(random.nextInt(2023),random.nextInt(528));
        }
    }
}

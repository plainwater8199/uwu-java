package com.uwu.study.dp.behavioral.visitor;


import com.uwu.study.dp.behavioral.visitor.person.PersonStructure;
import com.uwu.study.dp.behavioral.visitor.visitor.Visitor1;
import com.uwu.study.dp.behavioral.visitor.visitor.Visitor2;

/**
 * 表示一个作用于某对象结构中的各元素的操作。它使你可以在不改变各元素类的前提下定义作用于这些元素的新操作。
 * 访问者模式把数据结构和作用于结构上的操作解耦合，使得操作集合可相对自由地演化。
 *
 * 1.Visitor 抽象访问者角色，为该对象结构中具体元素角色声明一个访问操作接口。
 * 2.ConcreteVisitor.具体访问者角色，实现Visitor声明的接口。
 * 3.Element 定义一个接受访问操作(accept())，它以一个访问者(Visitor)作为参数。
 * 4.ConcreteElement 具体元素，实现了抽象元素(Element)所定义的接受操作接口。
 * 5.ObjectStructure 结构对象角色，这是使用访问者模式必备的角色。它具备以下特性：
 *    1、能枚举它的元素；可以提供一个高层接口以允许访问者访问它的元素；
 *    2、如有需要，可以设计成一个复合对象或者一个聚集（如一个列表或无序集合）。
 *
 *优点：
 * 1、符合单一职责原则：凡是适用访问者模式的场景中，元素类中需要封装在访问者中的操作必定是与元素类本身关系不大且是易变的操作，使用访问者模式一方面符合单一职责原则，
 *      另一方面，因为被封装的操作通常来说都是易变的，所以当发生变化时，就可以在不改变元素类本身的前提下，实现对变化部分的扩展。
 * 2、扩展性良好：元素类可以通过接受不同的访问者来实现对不同操作的扩展。
 *
 *
 */
public class VisitorPatternTest {
    public static void main(String[] args) {
        PersonStructure structure = new PersonStructure();
        Visitor1 visitor1 = new Visitor1();
        System.out.println("访问者1的访问记录：");
        structure.accept(visitor1);

        System.out.println("学生年龄的总和： "+visitor1.getStudentsAgeSum());
        System.out.println("老师年龄的总和： "+visitor1.getTeacherAgeSum());

        System.out.println("==========================================");


        Visitor2 visitor2 = new Visitor2();
        System.out.println("访问者2访问记录：");
        structure.accept(visitor2);
        System.out.println("学生成绩最高： "+visitor2.getMaxScore());
        System.out.println("老师工龄最高： "+visitor2.getMaxWorkYear());
    }
}

package com.uwu.study.dp.behavioral.observer;


/**
 * 指多个对象间存在一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 * 这种模式有时又称作发布-订阅模式、模型-视图模式，它是对象行为型模式。
 *
 * 优点：
 * 1、降低了目标与观察者之间的耦合关系，两者之间是抽象耦合关系。符合依赖倒置原则。
 * 2、目标与观察者之间建立了一套触发机制。
 *
 * 缺点：
 * 1、目标与观察者之间的依赖关系并没有完全解除，而且有可能出现循环引用。
 * 2、当观察者对象很多时，通知的发布会花费很多时间，影响程序的效率。
 */
public class ObserverPatternTest {
    public static void main(String[] args) {
        Subject subjectA = new ConcreteSubject("目标A");


        Observer observerA = new ConcreteObserver("张三",subjectA);
        Observer observerB = new ConcreteObserver("李四",subjectA);
        Observer observerC = new ConcreteObserver("王五",subjectA);

        subjectA.setState("更新了");




        subjectA.detach(observerA);

        subjectA.setState("放弃了");

    }
}

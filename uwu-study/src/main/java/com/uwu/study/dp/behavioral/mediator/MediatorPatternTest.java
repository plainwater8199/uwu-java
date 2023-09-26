package com.uwu.study.dp.behavioral.mediator;


import com.uwu.study.dp.behavioral.mediator.colleague.Colleague1;
import com.uwu.study.dp.behavioral.mediator.colleague.Colleague2;
import com.uwu.study.dp.behavioral.mediator.mediator.ConcreteMediator;

/**
 * 提供一个中介类，来封装一系列对象之间的交互关系，又叫调停模式
 * 优点：
 * 1、降低程序复杂度
 * 2、解耦
 * 缺点：
 * 1、系统稳定差
 * 2、逻辑复杂
 */
public class MediatorPatternTest {
    public static void main(String[] args) {
        ConcreteMediator mediator = new ConcreteMediator();
        Colleague1 colleague1 = new Colleague1(mediator);
        Colleague2 colleague2 = new Colleague2(mediator);

        mediator.setColleague1(colleague1);
        mediator.setColleague2(colleague2);

        colleague1.sendMessage("加加油");
        colleague2.sendMessage("一定成功");
    }
}

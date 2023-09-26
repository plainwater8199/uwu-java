package com.uwu.study.dp.behavioral.chainofresponsibility;


import com.uwu.study.dp.behavioral.chainofresponsibility.handler.FuDaoYuan;
import com.uwu.study.dp.behavioral.chainofresponsibility.handler.Handler;
import com.uwu.study.dp.behavioral.chainofresponsibility.handler.XiaoZhang;
import com.uwu.study.dp.behavioral.chainofresponsibility.handler.YuanZhang;

/**
 * 责任链（Chain of Responsibility）模式的定义：
 *  为了避免请求发送者与多个请求处理者耦合在一起，于是将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链；
 *  当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止。在责任链模式中，客户只需要将请求发送到责任链上即可，
 *  无须关心请求的处理细节和请求的传递过程，请求会自动进行传递。所以责任链将请求的发送者和请求的处理者解耦了。
 *
 *优点：
 * 1、降低对象之间的耦合度
 * 2、增强系统的可扩展性
 * 3、增强了对象指派职责的灵活性
 * 4、责任担当
 *
 *缺点：
 * 1、不能保证每个请求一定被处理
 * 2、长连性能有影响
 * 3、可靠性问题
 */
public class ChainOfResponsibilityTest {
    public static void main(String[] args) {
        Handler fudaoyuan = new FuDaoYuan();
        Handler yuanzhang = new YuanZhang();
        Handler xiaozhang = new XiaoZhang();

        fudaoyuan.setNext(xiaozhang);
        xiaozhang.setNext(yuanzhang);

        fudaoyuan.handlerRequest(30);
    }
}

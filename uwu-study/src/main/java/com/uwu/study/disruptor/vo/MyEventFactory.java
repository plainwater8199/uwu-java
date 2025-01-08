package com.uwu.study.disruptor.vo;

import com.lmax.disruptor.EventFactory;


/**
 * 创建一个事件类和一个事件工厂类。事件类用于定义要传递的数据结构，事件工厂类用于创建事件实例。
 */
public class MyEventFactory implements EventFactory<MyEvent> {
    @Override
    public MyEvent newInstance() {
        return new MyEvent();
    }
}

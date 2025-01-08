package com.uwu.study.disruptor.hanlder;

import com.lmax.disruptor.EventHandler;
import com.uwu.study.disruptor.vo.MyEvent;

/**
 * 消费者，处理事件
 */

public class MyEventHandler implements EventHandler<MyEvent> {
    private final String name;

    public MyEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(MyEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(name + " received: " + event.getValue());
    }
}

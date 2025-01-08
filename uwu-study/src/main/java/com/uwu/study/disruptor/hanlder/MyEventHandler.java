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
        // 处理事件
        // ...
        Long value = event.getValue();
        if (value % 2 == 0) {
            System.out.println("休息了。。。。。。。。。。");
            Thread.sleep(10000);
        }
        // 处理完成后，完成 Future
        event.getFuture().complete(event.getValue()*2);
    }
}

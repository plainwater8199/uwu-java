package com.uwu.study.disruptor.producer;

import com.lmax.disruptor.RingBuffer;
import com.uwu.study.disruptor.vo.MyEvent;


/**
 * 在Spring Boot的配置类中创建和配置Disruptor实例。这里设置为多生产者模式，并定义多个消费者。
 */
public class MyEventProducer {
    private final RingBuffer<MyEvent> ringBuffer;

    public MyEventProducer(RingBuffer<MyEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long value) {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            MyEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            event.setValue(value);  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}

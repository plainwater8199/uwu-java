package com.uwu.study.disruptor.config;


import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.uwu.study.disruptor.hanlder.MyEventHandler;
import com.uwu.study.disruptor.producer.MyEventProducer;
import com.uwu.study.disruptor.vo.MyEvent;
import com.uwu.study.disruptor.vo.MyEventFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在Spring Boot的配置类中创建和配置Disruptor实例。这里设置为多生产者模式，并定义多个消费者。
 */

@Configuration
public class DisruptorConfig {

    @Bean
    public Disruptor<MyEvent> disruptor() {
        int bufferSize = 1024; // RingBuffer的大小，必须是2的幂
        ExecutorService executor = Executors.newCachedThreadPool();
        MyEventFactory factory = new MyEventFactory();

        Disruptor<MyEvent> disruptor = new Disruptor<>(factory, bufferSize, executor,
                ProducerType.MULTI, new BlockingWaitStrategy());

        // 定义消费者
        MyEventHandler handler1 = new MyEventHandler("Handler1");
        MyEventHandler handler2 = new MyEventHandler("Handler2");

        // 绑定消费者
        disruptor.handleEventsWith(handler1, handler2);

        // 启动Disruptor
        disruptor.start();

        return disruptor;
    }

    @Bean
    public MyEventProducer myEventProducer(Disruptor<MyEvent> disruptor) {
        return new MyEventProducer(disruptor.getRingBuffer());
    }
}

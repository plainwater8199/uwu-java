package com.uwu.study.zllm.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    // 立即处理队列
    @Bean
    public Queue rateLimitQueue() {
        return new Queue("rate.limit.queue", true);
    }

    // 延迟处理队列
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");// 指定延迟消息的路由方式
        return new CustomExchange("rate.limit.delayed.exchange",
                "x-delayed-message",
                true, // 是否持久化
                false, // 是否自动删除
                args);
    }

    @Bean
    public Queue delayedQueue() {
        return new Queue("rate.limit.delayed.queue", true);
    }

    @Bean
    public Binding delayedBinding() {
        return BindingBuilder.bind(delayedQueue())
                .to(delayedExchange())
                .with("rate.limit.delayed.queue")
                .noargs();
    }

    // 失败队列
    @Bean
    public Queue failedQueue() {
        return new Queue("rate.limit.failed.queue", true);
    }

    // 绑定
    @Bean
    public Binding rateLimitBinding() {
        return BindingBuilder.bind(rateLimitQueue())
                .to(delayedExchange())
                .with("rate.limit.queue")
                .noargs();
    }
}

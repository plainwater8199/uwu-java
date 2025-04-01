package com.uwu.study.zllm;

import com.uwu.study.zllm.config.ResultCache;
import com.uwu.study.zllm.dto.RateLimitMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class RateLimitConsumer {

    @Autowired
    private ResultCache resultCache;

    @RabbitListener(queues = "rate.limit.queue")
    public void handleImmediate(RateLimitMessage message) {
        processMessage(message);
    }

    @RabbitListener(queues = "rate.limit.delayed.queue")
    public void handleDelayed(RateLimitMessage message) {
        processMessage(message);
    }

    @RabbitListener(queues = "rate.limit.failed.queue")
    public void handleFailed(RateLimitMessage message) {
        System.err.println("Failed to process message: " + message.getRequestId());
        // 可以持久化到数据库或人工干预
    }

    private void processMessage(RateLimitMessage message) {
        try {
            // 实际业务处理
            System.out.println("Processing message: " + message.getRequestId());
            // 获取当前时间
            LocalTime now = LocalTime.now();
            // 定义格式化规则
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String response = now.format(formatter)+"___"+message.getContent().length();
            resultCache.completeRequest(message.getRequestId(), response);
        } catch (Exception e) {
            System.err.println("Error processing message: " + message.getRequestId());
            throw e; // 触发重试机制
        }
    }
}

package com.uwu.study.zllm.service;

import com.uwu.study.zllm.listener.RedisSubscriber;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class MessageService {

    private final StringRedisTemplate redisTemplate;
    private final RedisMessageListenerContainer redisContainer;

    public MessageService(StringRedisTemplate redisTemplate, RedisMessageListenerContainer redisContainer) {
        this.redisTemplate = redisTemplate;
        this.redisContainer = redisContainer;
    }

    public String sendRequestAndWaitForResponse(String requestData) {
        String requestId = UUID.randomUUID().toString(); // 生成唯一 ID
        CountDownLatch latch = new CountDownLatch(1); // 计数器

        // 动态创建 RedisSubscriber
        RedisSubscriber subscriber = new RedisSubscriber(latch, requestId, redisTemplate);

        // 手动注册监听器
        redisContainer.addMessageListener(subscriber, new PatternTopic("request_channel"));


        // 发送请求
        redisTemplate.convertAndSend("request_channel", requestId + ":" + requestData+"_"+requestData.length());

        try {
            boolean success = latch.await(5, TimeUnit.SECONDS); // 等待 5 秒
            if (!success) {
                return "⏳ Timeout: No response received!";
            }
            return redisTemplate.opsForValue().get(requestId); // 获取存入 Redis 的响应
        } catch (InterruptedException e) {
            return "⚠️ Interrupted!";
        } finally {
            // 解除监听，避免内存泄漏
            redisContainer.removeMessageListener(subscriber);
        }
    }
}

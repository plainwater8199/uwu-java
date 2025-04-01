package com.uwu.study.zllm.listener;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisPublisher {
    private final StringRedisTemplate redisTemplate;

    public RedisPublisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(String channel, String message) {
        System.out.println("📢 发送 Redis 消息: " + channel + " -> " + message);
        redisTemplate.convertAndSend(channel, message);
    }
}

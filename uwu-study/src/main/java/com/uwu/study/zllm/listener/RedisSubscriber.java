package com.uwu.study.zllm.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class RedisSubscriber implements MessageListener {

    private final CountDownLatch latch;
    private final String requestId;
    private final StringRedisTemplate redisTemplate;

    public RedisSubscriber(CountDownLatch latch, String requestId, StringRedisTemplate redisTemplate) {
        this.latch = latch;
        this.requestId = requestId;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String response = new String(message.getBody(), StandardCharsets.UTF_8);

        System.out.println("RedisSubscriber---------oonMessage:"+response);
        // 检查消息是否匹配 requestId
        if (response.startsWith(requestId)) {
            String result = response.split(":")[1]; // 提取结果
            redisTemplate.opsForValue().set(requestId, result); // 存入 Redis 供后续查询
            latch.countDown(); // 解除阻塞
        }
    }
}

package com.uwu.study.redis.messagelistener.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyMessageListener implements MessageListener {

    private final RedisTemplate<String, Object> redisTemplate;

    public MyMessageListener(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 使用RedisTemplate的序列化器反序列化消息
        String channel = (String) redisTemplate.getStringSerializer()
                .deserialize(message.getChannel());

        Object body = redisTemplate.getValueSerializer()
                .deserialize(message.getBody());
        System.out.println("channel: " + channel);
        System.out.println("反序列化后的消息: " + body);
    }
}

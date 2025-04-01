package com.uwu.study.redis.subpub.handler;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

@Component
public class RedisResponseHandler implements MessageListener {

    private final ConcurrentMap<String, ResponseWrapper> pendingRequests = new ConcurrentHashMap<>();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        System.out.println("-------------------response---------------: " + channel);
        String response = (String) redisTemplate.getValueSerializer().deserialize(message.getBody());

        // 解析关联ID和实际响应内容
        String[] parts = response.split("\\|", 2);
        if (parts.length == 2) {
            String correlationId = parts[0];
            String actualResponse = parts[1];
            correlationId = correlationId.replace("\"", "");
            ResponseWrapper wrapper = pendingRequests.get(correlationId);
            if (wrapper != null) {
                wrapper.setResponse(actualResponse);
                wrapper.getLatch().countDown(); // 释放等待的线程
            }
        }
    }

    public ResponseWrapper registerRequest(String correlationId) {
        ResponseWrapper wrapper = new ResponseWrapper();
        pendingRequests.put(correlationId, wrapper);
        return wrapper;
    }

    public void unregisterRequest(String correlationId) {
        pendingRequests.remove(correlationId);
    }

    @Data
    public static class ResponseWrapper {
        private CountDownLatch latch = new CountDownLatch(1);
        private String response;
    }
}

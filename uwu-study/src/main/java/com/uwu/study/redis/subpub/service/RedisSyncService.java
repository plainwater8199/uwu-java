package com.uwu.study.redis.subpub.service;

import com.uwu.study.redis.subpub.handler.RedisResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisSyncService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisResponseHandler responseHandler;

    @Value("${redis.request.channel}")
    private String requestChannel;

    @Value("${redis.response.channel}")
    private String responseChannel;

    @Value("${redis.timeout.seconds:5}")
    private long timeoutSeconds;

    public String sendAndWait(String message) throws InterruptedException {
        String correlationId = UUID.randomUUID().toString();//用于匹配请求和响应
        RedisResponseHandler.ResponseWrapper wrapper = responseHandler.registerRequest(correlationId);

        try {
            // 发送请求（附带关联ID）
            String requestMessage = correlationId + "|" + message;
            redisTemplate.convertAndSend("requestChannel", requestMessage);

            // 等待响应（同步阻塞）
            boolean received = wrapper.getLatch().await(100, TimeUnit.SECONDS);

            if (!received) {
                throw new RuntimeException("等待响应超时");
            }
            if (wrapper.getResponse() == null) {
                throw new RuntimeException("收到空响应");
            }

            return wrapper.getResponse();
        } finally {
            responseHandler.unregisterRequest(correlationId);
        }
    }
}

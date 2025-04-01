package com.uwu.study.redis.subpub.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 服务端实现
 */


@Service
public class RedisRequestHandler implements MessageListener {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.response.channel}")
    private String responseChannel;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String request = redisTemplate.getStringSerializer().deserialize(message.getBody());
        String channel = new String(message.getChannel());
        System.out.println("-------------------request---------------: " + channel);
        // 解析关联ID和实际请求内容
        String[] parts = request.split("\\|", 2);
        if (parts.length == 2) {
            String correlationId = parts[0];
            String actualRequest = parts[1];

            // 处理业务逻辑
            String response = processRequest(actualRequest);

            // 发送响应（保持相同关联ID）
            String responseMessage = correlationId + "|" + response;
            redisTemplate.convertAndSend("responseChannel", responseMessage);
        }
    }

    private String processRequest(String request) {
        // 模拟处理耗时
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Processed: " + request + " at " + System.currentTimeMillis();
    }
}

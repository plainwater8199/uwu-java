package com.uwu.study.redis.messagelistener.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class FirstMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("FirstListener 收到消息 - 频道: " + new String(message.getChannel()));
        System.out.println("内容: " + new String(message.getBody()));
    }
}
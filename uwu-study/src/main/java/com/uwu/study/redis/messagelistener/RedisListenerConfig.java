package com.uwu.study.redis.messagelistener;

import com.uwu.study.redis.messagelistener.listener.MyMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Arrays;

@Configuration
public class RedisListenerConfig {

    @Bean
    public RedisMessageListenerContainer container(
            RedisConnectionFactory connectionFactory,
            MyMessageListener messageListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 订阅指定频道
//        container.addMessageListener(messageListener, new ChannelTopic("myChannel"));

        // 可以添加多个监听器和频道
        // container.addMessageListener(otherListener, new ChannelTopic("otherChannel"));

        // 订阅多个频道
        container.addMessageListener(messageListener, Arrays.asList(
                new ChannelTopic("channel1"),
                new ChannelTopic("channel2"),
                new ChannelTopic("channel3")
        ));


        return container;
    }
}
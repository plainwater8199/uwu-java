//package com.uwu.study.zllm.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//
//@Configuration
//public class RedisPubSubConfig {
//
//    @Bean
//    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        return container;
//    }
//}

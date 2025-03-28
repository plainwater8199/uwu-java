package com.uwu.study.rockemq.config;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfig {

    @Bean
    public RocketMQTemplate rocketMQTemplate(RocketMQProperties properties) {
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        DefaultMQProducer producer = new DefaultMQProducer(properties.getProducer().getGroup());
        producer.setNamesrvAddr(properties.getNameServer());
        rocketMQTemplate.setProducer(producer);
        return rocketMQTemplate;
    }
}

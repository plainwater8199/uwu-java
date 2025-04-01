package com.uwu.study.redis.subpub.config;

import com.uwu.study.redis.subpub.handler.RedisResponseHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.PostConstruct;

@Configuration
public class RedisResponseConfig {

    /**
     * 循环依赖

    @Autowired
    private RedisMessageListenerContainer container;

    @Autowired
    private RedisResponseHandler responseHandler;

    @Value("${redis.response.channel}")
    private String responseChannel;

    @Bean
    public ChannelTopic responseTopic() {
        return new ChannelTopic(responseChannel);
    }

    @PostConstruct
    public void registerListener() {
        container.addMessageListener(responseHandler, responseTopic());
    }
     */


    // 移除对 RedisResponseHandler 的自动注入
    private final RedisResponseHandler responseHandler;
    private final RedisMessageListenerContainer container;

    // 改为构造函数注入
    public RedisResponseConfig(RedisResponseHandler responseHandler,
                               RedisMessageListenerContainer container) {
        this.responseHandler = responseHandler;
        this.container = container;
    }
    @PostConstruct
    public void registerListener() {
        container.addMessageListener(responseHandler,  new ChannelTopic("responseChannel"));
    }

}

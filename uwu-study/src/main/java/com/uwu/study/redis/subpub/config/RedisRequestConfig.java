package com.uwu.study.redis.subpub.config;

import com.uwu.study.redis.subpub.handler.RedisRequestHandler;
import com.uwu.study.redis.subpub.handler.RedisResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class RedisRequestConfig {

    /**
     * 产生循环依赖

    @Resource
    private RedisMessageListenerContainer container;

    @Autowired
    private RedisRequestHandler requestHandler;

    @Value("${redis.request.channel}")
    private String requestChannel;

    @Bean
    public ChannelTopic requestTopic() {
        return new ChannelTopic(requestChannel);
    }

    @PostConstruct
    public void registerListener() {
        container.addMessageListener(requestHandler, requestTopic());
    }

     */

    private final RedisRequestHandler requestHandler;
    private final RedisMessageListenerContainer container;
    // 使用构造函数注入替代字段注入
    public RedisRequestConfig(RedisRequestHandler requestHandler,RedisMessageListenerContainer container) {
        this.container = container;
        this.requestHandler = requestHandler;
    }

    @PostConstruct
    public void registerListener() {
        container.addMessageListener(requestHandler,  new ChannelTopic("requestChannel"));
    }


//    // 将监听器注册移到单独的Bean定义中
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(
//            RedisConnectionFactory connectionFactory,
//            ChannelTopic requestTopic) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(requestHandler, requestTopic);
//        return container;
//    }
}

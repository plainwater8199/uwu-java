package com.uwu.study.netty.ws.websocketmessagebrokerconfigurer;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * 在上面的代码中，我们创建了一个名为WebSocketConfig的配置类，
 * 它实现了WebSocketMessageBrokerConfigurer接口。
 * 这个类用于配置WebSocket消息代理和注册STOMP（Simple Text Oriented Messaging Protocol）端点
 *
 * configureMessageBroker()：配置消息代理和目的地前缀
 *
 * registerStompEndpoints()：注册STOMP端点
 *
 * configureWebSocketTransport()：配置传输层参数
 *
 * configureClientInboundChannel()：配置入站通道
 *
 * configureClientOutboundChannel()：配置出站通道
 */


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //配置消息代理
        config.enableSimpleBroker("/topic");//启动内存消息代理
        config.setApplicationDestinationPrefixes("/app");//应用前缀
    }

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 STOMP 端点
        // allowedOrigins("*") 使用会无效，需要使用 allowedOriginPatterns("*")  allowedOriginPatterns 允许使用通配符 *，同时仍然支持 allowCredentials(true)
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        // 配置传输参数
        registration.setMessageSizeLimit(128 * 1024); // 消息大小限制
    }
}

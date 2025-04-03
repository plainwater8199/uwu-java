package com.uwu.study.netty.ws.websocketconfigurer.config;

import com.uwu.study.netty.ws.websocketconfigurer.handler.ServerWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketServerConfig implements WebSocketConfigurer {

    /**
     * WebSocketHandlerRegistry  注册处理器与路径  registry.addHandler(handler, path)
     * WebSocketHandler 处理连接/消息事件
     * @param registry
     */

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/my-websocket")
                .setAllowedOrigins("*");
    }


    /**
     * @Bean
     * 将返回的 WebSocketHandler 实例交给 Spring 容器管理。
     *
     * ServerWebSocketHandler
     * 自定义的消息处理器类，通常继承以下类之一：
     *
     * TextWebSocketHandler：处理文本消息
     *
     * BinaryWebSocketHandler：处理二进制消息
     *
     * 直接实现 WebSocketHandler 接口（需重写所有方法）
     * @return
     */
    @Bean
    public WebSocketHandler myHandler() {
        return new ServerWebSocketHandler();
    }
}

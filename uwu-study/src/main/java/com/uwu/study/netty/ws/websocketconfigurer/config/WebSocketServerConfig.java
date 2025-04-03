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

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/my-websocket")
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new ServerWebSocketHandler();
    }
}

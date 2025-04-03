package com.uwu.user.ws.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ClientWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("[客户端] 已连接到服务端");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("[客户端] 收到消息: " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("[客户端] 连接关闭");
    }
}

package com.uwu.study.netty.ws.websocketconfigurer.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ServerWebSocketHandler extends TextWebSocketHandler {
    // 线程安全的会话列表
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("[服务端] 新连接: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("[服务端] 收到消息: " + payload);

        // 广播给所有客户端
        for (WebSocketSession s : sessions) {
            try {
                s.sendMessage(new TextMessage("ECHO: " + payload));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("[服务端] 连接关闭: " + session.getId());
    }


    // 向所有客户端广播消息
    public void broadcast(String message) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }

    // 向特定客户端发送消息
    public void sendToClient(String sessionId, String message) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.getId().equals(sessionId) && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
                break;
            }
        }
    }

}

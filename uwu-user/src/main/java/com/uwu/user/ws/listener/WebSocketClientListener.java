package com.uwu.user.ws.listener;

import com.uwu.user.ws.handler.ClientWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;

@Component
public class WebSocketClientListener implements CommandLineRunner {

    private static final String WS_URL = "ws://localhost:8889/my-websocket";

    @Autowired
    private WebSocketClient webSocketClient;

    @Override
    public void run(String... args) throws Exception {
        WebSocketHandler handler = new ClientWebSocketHandler();
        webSocketClient.doHandshake(handler, WS_URL);
    }
}

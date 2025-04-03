package com.uwu.study.netty.ws.websocketconfigurer;

import com.uwu.study.netty.ws.websocketconfigurer.handler.ServerWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WScsController {

    @Autowired
    private ServerWebSocketHandler webSocketHandler;

    // 广播消息给所有客户端
    @GetMapping("/broadcast")
    public String broadcast(@RequestParam String message) throws IOException {
        webSocketHandler.broadcast("广播消息: " + message);
        return "已发送广播";
    }

    // 发送消息给特定客户端
    @GetMapping("/send-to-client")
    public String sendToClient(
            @RequestParam String sessionId,
            @RequestParam String message
    ) throws IOException {
        webSocketHandler.sendToClient(sessionId, "私信: " + message);
        return "已发送给客户端 " + sessionId;
    }
}

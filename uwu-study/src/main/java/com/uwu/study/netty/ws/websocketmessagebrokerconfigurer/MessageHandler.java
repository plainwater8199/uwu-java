package com.uwu.study.netty.ws.websocketmessagebrokerconfigurer;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageHandler {

    /**
     * 在 resources/template/index.html 设计了调用界面，可以通过调用界面进行访问。 http://localhost:8889/
     *
     * @param message
     * @return
     */
    @MessageMapping("/send")    // 监听客户端发送到"/app/send"的消息
    @SendTo("/topic/messages") // 将返回值广播到"/topic/messages"
    public Message handleMessage(String message) {
        System.out.println( "Received message: " + message );
        return new Message("收到了！！！！！"+message,"water-handler");

    }
}

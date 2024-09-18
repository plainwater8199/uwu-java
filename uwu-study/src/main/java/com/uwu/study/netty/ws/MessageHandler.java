package com.uwu.study.netty.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageHandler {

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message handleMessage(String message) {
        return new Message("Hello, World!","water");

    }
}

package com.uwu.study.netty.ws.websocketmessagebrokerconfigurer;

import lombok.Data;

@Data
public class Message {
    private String content;
    private String sender;

    public Message() {

    }

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }
}

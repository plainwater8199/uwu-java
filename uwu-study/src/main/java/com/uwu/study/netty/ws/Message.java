package com.uwu.study.netty.ws;

import lombok.Data;

@Data
public class Message {
    private String content;
    private String sender;

    public Message(String s, String water) {
        this.content = s;
        this.sender = water;
    }
}

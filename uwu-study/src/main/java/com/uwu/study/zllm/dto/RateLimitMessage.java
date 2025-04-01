package com.uwu.study.zllm.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RateLimitMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String requestId;
    private String content;

    public RateLimitMessage(String requestId, String content) {
        this.requestId = requestId;
        this.content = content;
    }
}

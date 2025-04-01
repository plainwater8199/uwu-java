package com.uwu.study.zllm.service;

import lombok.Data;

@Data
public class QueueTask {
    private String requestId;
    private String requestData;
    public QueueTask(String requestId, String requestData) {
        this.requestId = requestId;
        this.requestData = requestData;
    }
}

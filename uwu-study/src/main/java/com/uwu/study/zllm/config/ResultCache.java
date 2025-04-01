package com.uwu.study.zllm.config;

import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ResultCache {
    private final ConcurrentMap<String, CompletableFuture<String>> pendingRequests = new ConcurrentHashMap<>();

    public CompletableFuture<String> createRequest(String correlationId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);
        return future;
    }

    public void completeRequest(String correlationId, String result) {
        CompletableFuture<String> future = pendingRequests.remove(correlationId);
        if (future != null) {
            future.complete(result);
        }
    }
}

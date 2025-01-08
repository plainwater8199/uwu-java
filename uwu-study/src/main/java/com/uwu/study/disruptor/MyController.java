package com.uwu.study.disruptor;

import com.uwu.study.disruptor.producer.MyEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class MyController {

    private final MyEventProducer myEventProducer;

    @Autowired
    public MyController(MyEventProducer myEventProducer) {
        this.myEventProducer = myEventProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishEvent(@RequestParam("value") long value) {
        try {
            CompletableFuture<Long> future = new CompletableFuture<>();
            myEventProducer.onData(value, future);

            try {
                // 等待消息处理结果，设置超时时间为5秒
                long result = future.get(3, TimeUnit.SECONDS);
                return ResponseEntity.ok("Processed result: " + result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Processing interrupted");
            } catch (ExecutionException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Processing failed: " + e.getCause().getMessage());
            } catch (TimeoutException e) {
                return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Processing timed out");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

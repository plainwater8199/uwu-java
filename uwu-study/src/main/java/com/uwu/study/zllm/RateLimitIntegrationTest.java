package com.uwu.study.zllm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RateLimitIntegrationTest {

//    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRateLimiting() throws InterruptedException {
        // 测试正常请求
        for (int i = 0; i < 10; i++) {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "/api/rate-limited", "test-" + i, String.class);
            assertEquals(202, response.getStatusCodeValue());
        }

        // 测试超限请求会被延迟
        ResponseEntity<String> delayedResponse = restTemplate.postForEntity(
                "/api/rate-limited", "delayed-request", String.class);
        assertEquals(202, delayedResponse.getStatusCodeValue());

        // 测试字符数限制
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("1234567890"); // 1000字符
        }
        ResponseEntity<String> charResponse = restTemplate.postForEntity(
                "/api/rate-limited", sb.toString(), String.class);
        assertEquals(202, charResponse.getStatusCodeValue());

        // 等待处理完成
        Thread.sleep(2000);
    }

    @Test
    public void testConcurrentRequests() throws InterruptedException {
        int threadCount = 30;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    ResponseEntity<String> response = restTemplate.postForEntity(
                            "/api/rate-limited", "concurrent-" + index, String.class);
                    assertTrue(response.getStatusCode().is2xxSuccessful());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // 等待所有请求被处理
        Thread.sleep(3000);
    }
}
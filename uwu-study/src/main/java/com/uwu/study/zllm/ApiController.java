package com.uwu.study.zllm;


import com.uwu.study.zllm.config.ResultCache;
import com.uwu.study.zllm.dto.StateItem;
import com.uwu.study.zllm.hanlder.MqDelayedHandler;
import com.uwu.study.zllm.listener.RedisPublisher;
import com.uwu.study.zllm.listener.RedisSubscriber;
import com.uwu.study.zllm.service.ResultAwareQueueService;
import com.uwu.study.zllm.vo.RateLimitedReq;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.Duration;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

@RestController

public class ApiController {

    private final RateLimiterRegistry rateLimiterRegistry;
    private final ResultAwareQueueService resultAwareQueueService;

    private final StringRedisTemplate redisTemplate;
    private final RedisPublisher redisPublisher;
    private final RedisMessageListenerContainer redisContainer;


    @Autowired
    private ResultCache resultCache;

    public ApiController(RateLimiterRegistry rateLimiterRegistry,
                         StringRedisTemplate redisTemplate,
                         RedisPublisher redisPublisher,
                         RedisMessageListenerContainer redisContainer,
                         ResultAwareQueueService resultAwareQueueService) {
        this.rateLimiterRegistry = rateLimiterRegistry;
        this.resultAwareQueueService = resultAwareQueueService;
        this.redisTemplate = redisTemplate;
        this.redisPublisher = redisPublisher;
        this.redisContainer = redisContainer;
    }


    @Autowired
    private MqDelayedHandler delayedHandler;

    @PostMapping("/api/rate-limited")
    public ResponseEntity<String> handleRequest(@RequestBody RateLimitedReq req) {
        String requestId = UUID.randomUUID().toString();
        try {
            delayedHandler.handleRequest(requestId, req.getContent());
            return ResponseEntity.accepted()
                    .body("Request accepted, ID: " + requestId);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error processing request: " + e.getMessage());
        }
    }

    @GetMapping("/api/rateLimitedTestT")
    public void rateLimitedTestT() {
        delayedHandler.print();
    }


    @GetMapping("/api/rateLimitedTest")
    public void rateLimitedTest() {

        final int totalCalls = 50;
        final int concurrentThreads = 100; // 控制并发数
        ExecutorService executor = Executors.newFixedThreadPool(concurrentThreads);
        CountDownLatch latch = new CountDownLatch(totalCalls);

        for (int i = 0; i < totalCalls; i++) {
            executor.submit(() -> {
                String requestId = UUID.randomUUID().toString();
                String content = getRandomContent();
                try {


                    /**
                     * 分布式情况下实现
                     */
                    // 存入 Redis，并设置过期时间，防止内存泄漏
//                    redisTemplate.opsForValue().set(requestId, "PENDING", Duration.ofSeconds(100));
//                    delayedHandler.handleRequest(requestId, content);
//
//                    // 订阅 Redis Pub/Sub，等待响应
//                    CountDownLatch latch = new CountDownLatch(1);
//                    // 动态创建 RedisSubscriber
//                    RedisSubscriber subscriber = new RedisSubscriber(latch, requestId, redisTemplate);
//
//                    // 手动注册监听器
//                    redisContainer.addMessageListener(subscriber, new PatternTopic("response_channel"));
//
//                    // 发送请求
//                    redisTemplate.convertAndSend("request_channel", requestId + ":" + requestData);
//
//
//                    try {
//                        boolean success = latch.await(5, TimeUnit.SECONDS); // 等待 5 秒
//                        if (!success) {
//                            System.out.println( "⏳ Timeout: No response received!");
//                        }
//
//                        // 获取存入 Redis 的响应
//                        System.out.println( redisTemplate.opsForValue().get(requestId));
//                    } catch (InterruptedException e) {
//                        System.out.println( "⚠️ Interrupted!");
//                    }
//                    redisTemplate.getConnectionFactory().getConnection().subscribe((message1, pattern) -> {
//                        String response = new String(message1.getBody());
//                        if (response.startsWith(requestId)) {
//                            redisTemplate.opsForValue().set(requestId, response.split(":")[1]); // 存入最终结果
//                            latch.countDown(); // 解除阻塞
//                        }
//                    }, RESPONSE_CHANNEL.getBytes());

                    // 等待最大 5 秒
//                    boolean success = latch.await(5, TimeUnit.SECONDS);

//                    System.out.println(success ? redisTemplate.opsForValue().get(requestId) : "Timeout");

                    /**
                     *  本地情况下实现--异步转同步
                     */
                    CompletableFuture<String> future = resultCache.createRequest(requestId);
                    delayedHandler.handleRequest(requestId, content);

                    // 阻塞等待结果
                    String s = future.get(1000, TimeUnit.SECONDS);
                    System.out.println("结果："+s);

                } catch (Exception e) {
                    System.out.println("error:"+e.getMessage());
                }
            });
        }

//        try {
//            latch.await(); // 等待所有调用完成
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
        executor.shutdown();

    }

    private String getRandomContent() {
        String[] strings = {
                "aaaaaaaa10",
                "aaaaaaaaaaaaaaaaaa20",
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbb30",
                "cccccccccccccccccccccccccccccccccccccc40",
                "dddddddddddddddddddddddddddddddddddddddddddddddd50",
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee60",
                "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff70"
        };

        Random random = new Random();
        return strings[random.nextInt(strings.length)];
    }


    @GetMapping("/api/LimiterTest")
    public void getLimiterTestUser() {

        final int totalCalls = 100;
        final int concurrentThreads = 10; // 控制并发数
        ExecutorService executor = Executors.newFixedThreadPool(concurrentThreads);
        CountDownLatch latch = new CountDownLatch(totalCalls);

        for (int i = 0; i < totalCalls; i++) {
            final int index = i;
            int finalI = i;
            executor.submit(() -> {
                try {
                    DeferredResult<String> result = processRequest("userApiLimiter", "user", "获取用户数据: " + finalI);
                    System.out.println("调用 " + (index+1) + " 次，结果: " + result.getResult());
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await(); // 等待所有调用完成
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        executor.shutdown();

    }


    @GetMapping("/api/users/{id}")
    public DeferredResult<String> getUser(@PathVariable String id) {
        return processRequest(
                "userApiLimiter",
                "user",
                "获取用户数据: " + id
        );
    }

    @GetMapping("/api/products/{id}")
    public DeferredResult<String> getProduct(@PathVariable String id) {
        return processRequest(
                "productApiLimiter",
                "product",
                "获取商品数据: " + id
        );
    }

    @PostMapping("/api/orders")
    public DeferredResult<String> createOrder(@RequestBody String orderData) {
        return processRequest(
                "orderApiLimiter",
                "order",
                "创建订单: " + orderData
        );
    }

    private DeferredResult<String> processRequest(String limiterName,
                                                  String queueType,
                                                  String requestData) {
        DeferredResult<String> deferredResult = new DeferredResult<>(
                15000L, "请求超时，请稍后重试");

        RateLimiter limiter = rateLimiterRegistry.rateLimiter(limiterName);

        if (limiter.acquirePermission()) {
            // 立即处理
            String result = "立即处理: " + requestData;
            deferredResult.setResult(result);
        } else {
            // 加入队列并等待结果
            CompletableFuture<String> resultFuture = resultAwareQueueService.addToQueue(requestData);

            // 当Future完成时设置DeferredResult的结果
            try {
                String  s = resultFuture.get();
                deferredResult.setResult(s);

                // 设置超时处理
                deferredResult.onTimeout(() -> {
                    resultFuture.cancel(true);
                    deferredResult.setErrorResult("请求处理超时");
                });
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return deferredResult;
    }
}

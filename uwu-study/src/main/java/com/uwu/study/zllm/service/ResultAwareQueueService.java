package com.uwu.study.zllm.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class ResultAwareQueueService {
    // 存储待处理的请求和对应的结果承诺
    private final Map<String, CompletableFuture<String>> resultFutures = new ConcurrentHashMap<>();

    // 任务队列
    private final BlockingQueue<QueueTask> taskQueue = new LinkedBlockingQueue<>();

    // 线程池处理队列任务
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    // 定时任务线程池（用于超时处理）
    private final ScheduledExecutorService timeoutExecutor = Executors.newScheduledThreadPool(10);
    @PostConstruct
    public void init() {
        startQueueConsumer();
    }

    // 启动队列消费者
    private void startQueueConsumer() {
        for (int i = 0; i < 10; i++) { // 启动多个消费者
            executorService.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        QueueTask task = taskQueue.take();
                        processTask(task);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
    }

    // 处理任务并完成结果承诺
    private void processTask(QueueTask task) {
        try {
            // 模拟业务处理耗时
            Thread.sleep(200);
            String result = "处理完成: " + task.getRequestData();

            // 完成对应的Future
            CompletableFuture<String> future = resultFutures.remove(task.getRequestId());
            if (future != null) {
                future.complete(result);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            CompletableFuture<String> future = resultFutures.remove(task.getRequestId());
            if (future != null) {
                future.completeExceptionally(e);
            }
        } catch (Exception e) {
            CompletableFuture<String> future = resultFutures.remove(task.getRequestId());
            if (future != null) {
                future.completeExceptionally(e);
            }
        }
    }

    // 添加任务到队列并返回结果Future
    public CompletableFuture<String> addToQueue(String requestData) {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<String> future = new CompletableFuture<>();
        resultFutures.put(requestId, future);

        // 设置超时处理
        timeoutExecutor.schedule(() -> {
            if (future != null && !future.isDone()) {
                future.completeExceptionally(new TimeoutException("处理超时"));
                resultFutures.remove(requestId);
            }
        }, 15, TimeUnit.SECONDS);

        taskQueue.add(new QueueTask(requestId, requestData));
        return future;
    }



    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}
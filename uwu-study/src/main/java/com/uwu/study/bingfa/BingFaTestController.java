package com.uwu.study.bingfa;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class BingFaTestController {
    private final AtomicInteger requestCount = new AtomicInteger(0);

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/response")
    public String handleRequest(@RequestParam("name") String name) {
        // 模拟处理时间
        try {
            Thread.sleep(100);
            return "Hello: " + name + "!,---"+requestCount.incrementAndGet()+"---";

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "error";
    }

    /**
     *  通过 CountDownLatch 实现 10000个请求同时触发
     */
    @GetMapping("/testStart1")
    public void testStart() {
        System.out.println("---------------------开始测试-----------------------");

        String urlTemplate = "http://localhost:8012/response?name=";
        int numberOfRequests = 10000;
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(100); // 创建一个具有100个线程的线程池

        for (int i = 0; i < numberOfRequests; i++) {
            String name = "Name" + i;
            String url = urlTemplate + name;
            executor.submit(() -> {
                try {
                    latch.await();//等待所有线程都准备好
                    String response = restTemplate.getForObject(url, String.class);
                    System.out.println("Response for " + name + ": " + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("---------------------所有线程准备好------------------------");
        latch.countDown(); // 所有线程同时开始请求
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }


        System.out.println("---------------------测试结束-----------------------");
    }





    /**
     *  通过 Semaphore 实现 10000个请求同时触发
     */
    @GetMapping("/testStart2")
    public void testStart2() {
        System.out.println("---------------------开始测试-----------------------");

        String urlTemplate = "http://localhost:8012/response?name=";
        int numberOfRequests = 10000;
        Semaphore semaphore = new Semaphore(10000); // 创建一个信号量，并设置最大可用数量为10000
        ExecutorService executor = Executors.newFixedThreadPool(100); // 创建一个具有100个线程的线程池

        for (int i = 0; i < numberOfRequests; i++) {
            String name = "Name" + i;
            String url = urlTemplate + name;
            executor.submit(() -> {
                try {
                    semaphore.acquire(); // 获取一个信号量
                    String response = restTemplate.getForObject(url, String.class);
                    System.out.println("Response for " + name + ": " + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("---------------------所有线程准备好------------------------");
        semaphore.release(); // 释放一个信号量

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }


        System.out.println("---------------------测试结束-----------------------");
    }





    /**
     *  通过 CyclicBarrier 实现 10000个请求同时触发
     */
    @GetMapping("/testStart3")
    public void testStart3() throws InterruptedException {
        System.out.println("---------------------开始测试-----------------------");

        String urlTemplate = "http://localhost:8012/response?name=";
        int numberOfRequests = 350;
        int threadPoolSize = 100; // 线程池大小
        CyclicBarrier barrier = new CyclicBarrier(threadPoolSize,()->{
            System.out.println("----------满了，开始执行------------");
        });
        ExecutorService executor = Executors.newFixedThreadPool(100); // 创建一个具有100个线程的线程池

        for (int i = 0; i < numberOfRequests; i++) {
            String name = "Name" + i;
            String url = urlTemplate + name;
            int finalI = i;
            executor.submit(() -> {
                try {
                    barrier.await();//所有线程等待，直到所有线程都准备好
                    String response = restTemplate.getForObject(url, String.class);
                    System.out.println("Response for " + name + ": " + response);


                    // 重置CyclicBarrier以便下一批任务使用
                    if ((finalI + 1) % threadPoolSize == 0 || finalI +1 == numberOfRequests) {
                        System.out.println("----------重置Barrier-:"+ finalI +"-------");
                        barrier.reset();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


        }


        System.out.println("-----------没有了呀-----------");
//        barrier.reset(); // 重置CyclicBarrier以便下一批任务使用   -- 手动执行
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }


        System.out.println("---------------------测试结束-----------------------");
    }


}

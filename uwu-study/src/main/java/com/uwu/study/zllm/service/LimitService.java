package com.uwu.study.zllm.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LimitService {
    private static final int MAX_REQUESTS = 20;
    private static final int MAX_CHARS = 1000;
    private static final String REQ_COUNT_PREFIX = "rl:count:";
    private static final String CHAR_COUNT_PREFIX = "rl:chars:";
    private static final int KEY_EXPIRE = 600; // seconds

    private static final int MAX_WINDOW_SEARCH = 10; // 最大搜索窗口数

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;




    public RateLimitResult tryAcquire2(String content) {
        long currentSecond = System.currentTimeMillis() / 1000;
        ValueOperations<String, Integer> ops = redisTemplate.opsForValue();
        int contentLength = content != null ? content.length() : 0;

        // 1. 首先尝试当前时间窗口
        RateLimitResult result = tryWindow(ops, currentSecond, contentLength);
        if (result.isAllowed()) {
            return result;
        }

        // 2. 如果当前窗口已满，寻找下一个可用窗口
        for (int i = 1; i <= MAX_WINDOW_SEARCH; i++) {
            result = tryWindow(ops, currentSecond + i, contentLength);
            if (result.isAllowed()) {
                return result;
            }
        }

        // 3. 所有窗口都不可用
        return new RateLimitResult(false, -1);
    }

    private RateLimitResult tryWindow(ValueOperations<String, Integer> ops,
                                      long windowSecond, int contentLength) {
        String reqKey = REQ_COUNT_PREFIX + windowSecond;
        String charKey = CHAR_COUNT_PREFIX + windowSecond;

        // 初始化计数器(如果不存在)
        ops.setIfAbsent(reqKey, 0, KEY_EXPIRE, TimeUnit.SECONDS);
        ops.setIfAbsent(charKey, 0, KEY_EXPIRE, TimeUnit.SECONDS);

        // 使用Lua脚本保证原子性
        String luaScript =
                "local reqKey = KEYS[1]\n" +
                        "local charKey = KEYS[2]\n" +
                        "local maxReq = tonumber(ARGV[1])\n" +
                        "local maxChar = tonumber(ARGV[2])\n" +
                        "local contentLen = tonumber(ARGV[3])\n" +
                        "local expire = tonumber(ARGV[4])\n" +
                        "\n" +
                        "local currentReq = tonumber(redis.call('GET', reqKey) or 0\n" +
                        "local currentChar = tonumber(redis.call('GET', charKey) or 0\n" +
                        "\n" +
                        "if currentReq >= maxReq or (currentChar + contentLen) > maxChar then\n" +
                        "    return {0, 0}\n" +
                        "end\n" +
                        "\n" +
                        "redis.call('INCR', reqKey)\n" +
                        "redis.call('INCRBY', charKey, contentLen)\n" +
                        "redis.call('EXPIRE', reqKey, expire)\n" +
                        "redis.call('EXPIRE', charKey, expire)\n" +
                        "\n" +
                        "return {1, currentReq + 1, currentChar + contentLen}";

        @SuppressWarnings("unchecked")
        List<Long> result = (List<Long>) redisTemplate.execute(
                new DefaultRedisScript<>(luaScript, List.class),
                Arrays.asList(reqKey, charKey),
                MAX_REQUESTS, MAX_CHARS, contentLength, KEY_EXPIRE
        );

        if (result != null && !result.isEmpty() && result.get(0) == 1L) {
            return new RateLimitResult(true, windowSecond);
        }
        return new RateLimitResult(false, windowSecond);
    }








    public RateLimitResult tryAcquire(String content) {
        long currentSecond = System.currentTimeMillis() / 1000;

        // 使用Redis的原子操作
        ValueOperations<String, Integer> ops = redisTemplate.opsForValue();


        for(int i = 0; i < 1000; i++){
            currentSecond = currentSecond + i;
            String reqKey = REQ_COUNT_PREFIX + currentSecond;
            String charKey = CHAR_COUNT_PREFIX + currentSecond;
            // 初始化计数器(如果不存在)
            ops.setIfAbsent(reqKey, 0, KEY_EXPIRE, TimeUnit.SECONDS);
            ops.setIfAbsent(charKey, 0, KEY_EXPIRE, TimeUnit.SECONDS);

            // 获取当前计数
            Integer currentReq = ops.get(reqKey);
            Integer currentChars = ops.get(charKey);
            int contentLength = content != null ? content.length() : 0;

            // 检查当前秒是否可处理
            if (currentReq != null && currentReq < MAX_REQUESTS &&
                    currentChars != null && (currentChars + contentLength) <= MAX_CHARS) {

                // 原子递增
                Long newReqCount = ops.increment(reqKey);
                Long newCharCount = ops.increment(charKey, contentLength);

                if (newReqCount != null && newReqCount <= MAX_REQUESTS &&
                        newCharCount != null && newCharCount <= MAX_CHARS) {
                    return new RateLimitResult(true, currentSecond);
                }

                // 如果递增后超限，回滚
                if (newReqCount != null && newReqCount > MAX_REQUESTS) {
                    ops.decrement(reqKey);
                }
                if (newCharCount != null && newCharCount > MAX_CHARS) {
                    ops.decrement(charKey, contentLength);
                }
            }
        }



//        // 尝试下一秒
//        long nextSecond = currentSecond + 1;
//        String nextReqKey = REQ_COUNT_PREFIX + nextSecond;
//        String nextCharKey = CHAR_COUNT_PREFIX + nextSecond;
//
//        ops.setIfAbsent(nextReqKey, 0, KEY_EXPIRE, TimeUnit.SECONDS);
//        ops.setIfAbsent(nextCharKey, 0, KEY_EXPIRE, TimeUnit.SECONDS);
//
//        Integer nextReq = ops.get(nextReqKey);
//        Integer nextChars = ops.get(nextCharKey);
//
//        if (nextReq != null && nextReq < MAX_REQUESTS &&
//                nextChars != null && (nextChars + contentLength) <= MAX_CHARS) {
//
//            Long newNextReqCount = ops.increment(nextReqKey);
//            Long newNextCharCount = ops.increment(nextCharKey, contentLength);
//
//            if (newNextReqCount != null && newNextReqCount <= MAX_REQUESTS &&
//                    newNextCharCount != null && newNextCharCount <= MAX_CHARS) {
//                return new RateLimitResult(true, nextSecond);
//            }
//
//            // 回滚
//            if (newNextReqCount != null) ops.decrement(nextReqKey);
//            if (newNextCharCount != null) ops.decrement(nextCharKey, contentLength);
//        }
//
        return new RateLimitResult(false, -1);
    }

    @Data
    public static class RateLimitResult {
        private final boolean allowed;
        private final long processAtSecond;

        public RateLimitResult(boolean allowed, long processAtSecond) {
            this.allowed = allowed;
            this.processAtSecond = processAtSecond;
        }

        // getters...
    }

}

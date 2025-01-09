package com.uwu.study.localcache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CaffeineCacheTest {

    public static void main(String[] args) throws Exception {
        //创建guava cache
        Cache<String, String> loadingCache = Caffeine.newBuilder()
                //cache的初始容量
                .initialCapacity(5)
                //cache最大缓存数
                .maximumSize(10)
                //设置写缓存后n秒钟过期
                .expireAfterWrite(5, TimeUnit.SECONDS)
                //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                .build();
        String key = "key";
        // 往缓存写数据
        loadingCache.put(key, "v");

        // 获取value的值，如果key不存在，获取value后再返回
        String value = loadingCache.get(key, CaffeineCacheTest::getValueFromDB);
        System.out.println("value: " + value);

        // 删除key
        loadingCache.invalidate(key);


//        Thread.sleep(6000);
        // 获取value的值，如果key不存在，获取value后再返回
        String value2 = loadingCache.get(key, CaffeineCacheTest::getValueFromDB);
        System.out.println("value2: " + value2);


        // 删除key
        loadingCache.invalidate(key);
    }

    private static String getValueFromDB(String key) {
        return "v-DB";
    }
}

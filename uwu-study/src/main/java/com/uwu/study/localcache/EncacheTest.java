package com.uwu.study.localcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class EncacheTest {
    public static void main(String[] args) throws Exception {
        // 声明一个cacheBuilder
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("encacheInstance", CacheConfigurationBuilder
                        //声明一个容量为20的堆内缓存
                        .newCacheConfigurationBuilder(String.class,String.class, ResourcePoolsBuilder.heap(20)))
                .build(true);
        // 获取Cache实例
        Cache<String,String> myCache =  cacheManager.getCache("encacheInstance", String.class, String.class);
        // 写缓存
        myCache.put("key","v");
        // 读缓存
        String value = myCache.get("key");
        System.out.println(value);
        // 移除换粗
        cacheManager.removeCache("myCache");
        cacheManager.close();
    }
}

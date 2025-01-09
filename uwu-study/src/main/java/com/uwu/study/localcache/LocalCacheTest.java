package com.uwu.study.localcache;

public class LocalCacheTest {
    public static void main(String[] args) {
        /*
        根据HashMap自定义实现本地缓存
         */
        LRUCache cache = new LRUCache(10);
        cache.put("name", "water");
        cache.put("age", "25");
        System.out.println(cache.get("name")+"---"+cache.get("age"));

    }

}

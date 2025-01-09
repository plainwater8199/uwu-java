package com.uwu.study.localcache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCacheTest {


    //创建guava cache
    static Cache<String, String> loadingCache = CacheBuilder.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(10)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(5, TimeUnit.SECONDS)
            //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
            //.expireAfterAccess(17, TimeUnit.SECONDS)
            .build();




    public static void main(String[] args) throws Exception {

        String key = "key";
        // 往缓存写数据
        loadingCache.put(key, "v");


        String value = getValueFromGuava(key);
        // 获取value的值，如果key不存在，调用collable方法获取value值加载到key中再返回
        System.out.println(value);

        Thread.sleep(10000);


        String value2 = getValueFromGuava(key);
        System.out.println(value2);

        // 删除key
        loadingCache.invalidate(key);

        String value3 = getValueFromGuava(key);
        System.out.println(value3);
    }

    private static String getValueFromGuava(String key) throws ExecutionException {
        return  loadingCache.get(key, new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 这里可以做一些异步操作，比如从数据库获取数据
                return getValueFromDB(key);
            }
        });

    }

    private static String getValueFromDB(String key) {
        return "v222222";
    }
}

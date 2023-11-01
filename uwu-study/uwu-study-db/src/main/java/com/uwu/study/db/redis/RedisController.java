package com.uwu.study.db.redis;

import com.uwu.study.db.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class RedisController {

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "/redis/set")
    public boolean redisset(String key, String value){
        System.out.println(key+"--"+value);
        return redisUtil.set(key,value);
    }

    @RequestMapping(value = "/redis/get")
    public Object redisget(String key){
        System.out.println(redisUtil.get(key));
        return redisUtil.get(key);
    }

    @RequestMapping(value = "/redis/expire")
    public boolean expire(String key,long ExpireTime){
        return redisUtil.expire(key,ExpireTime);
    }

}

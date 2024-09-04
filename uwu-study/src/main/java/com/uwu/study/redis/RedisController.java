package com.uwu.study.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("redis/")
public class RedisController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("test")
    public String test() {
        redisTemplate.opsForValue().set("name", "water");

        String name = redisTemplate.opsForValue().get("name");
        return "redis hello :"+name;
    }
}

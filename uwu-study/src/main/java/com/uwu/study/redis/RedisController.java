package com.uwu.study.redis;

import com.uwu.study.redis.messagelistener.MessagePublisher;
import com.uwu.study.redis.subpub.service.RedisSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("redis/")
public class RedisController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisSyncService redisSyncService;

    @RequestMapping("test")
    public String test() {
        redisTemplate.opsForValue().set("name", "water");

        String name = redisTemplate.opsForValue().get("name");
        return "redis hello :"+name;
    }




    @GetMapping("/sync-call")
    public String makeSyncCall(@RequestParam String message) {
        try {
            return redisSyncService.sendAndWait(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "请求被中断";
        } catch (Exception e) {
            return "请求失败: " + e.getMessage();
        }
    }

    @Resource
    private MessagePublisher publisher;



    @PostMapping("/send")
    public String sendMessage(@RequestParam String channel,
                              @RequestParam String content) {
        publisher.publish(channel, content);
        return "消息已发送到频道: " + channel;
    }
}

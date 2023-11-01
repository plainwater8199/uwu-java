package com.uwu.study.db.test;

//import com.uwu.study.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class TestDBController {

//    @Resource
//    private RedisUtil redisUtil;

    @RequestMapping(value = "/testDBCall")
    public void apiCall(HttpServletRequest request, HttpServletResponse response){
//        System.out.println(redisUtil.get("name"));
        System.out.println("123");
    }
}

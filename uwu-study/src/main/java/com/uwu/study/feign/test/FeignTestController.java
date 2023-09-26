package com.uwu.study.feign.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FeignTestController {

//    @Autowired
//    private TestApi testApi;


    //注入动态FeignClient对象
    @Autowired
    private DynamicClient dynamicClient;

    @PostMapping("/test/feign/test")
    public void feign() {
//        testApi.feignTest();
        dynamicClient.executePostApi("misc-service","/test/feignTest","{}");
    }

    @PostMapping("/test/feign/test2")
    public void feign2() {
//        testApi.feignTest();
//        dynamicClient.executePostApi("127.0.0.1:8205","/test/feignTest","{}");
    }


    @PostMapping("/test/feign/test3")
    public void feign3() {
        dynamicClient.executePostApi("filecenter-service","/test/feignTest","{}");
    }



}

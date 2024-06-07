package com.uwu.study.filterandInterceptor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class Tes2Controller {
    @RequestMapping("/login")
    @ResponseBody
    public String login() {
        System.out.println("login .......");
        return "登录";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register() {
        System.out.println("register-----------");
        return "register";
    }


    @RequestMapping("/callTest")
    @ResponseBody
    public String callTest() {
        System.out.println("call-----------");
        return "call";
    }



    @GetMapping("/user/{userId}/query")
    public String user(@PathVariable("userId") String userId) {
        System.out.println("query-----------"+userId);
        return "123";
    }
}

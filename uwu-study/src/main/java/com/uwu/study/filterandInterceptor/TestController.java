package com.uwu.study.filterandInterceptor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/login")
    @ResponseBody
    public String login() {
        System.out.println("login .......");
        return "登录";
    }

    @RequestMapping("/register")
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
}

package com.uwu.user.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class TestController {

    @RequestMapping(value = "/user/test")
    public void userTest(HttpServletRequest request, HttpServletResponse response){
        System.out.println("123");
    }
}

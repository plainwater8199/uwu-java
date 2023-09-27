package com.uwu.study.db.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class TestDBController {
    @RequestMapping(value = "/testDBCall")
    public void apiCall(HttpServletRequest request, HttpServletResponse response){
        System.out.println("123");
    }
}

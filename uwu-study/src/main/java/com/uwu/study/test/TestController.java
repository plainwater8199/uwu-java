package com.uwu.study.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@RestController
@Slf4j
public class TestController {
    @RequestMapping(value = "/testCall")
    public void apiCall(HttpServletRequest request, HttpServletResponse response){
        System.out.println("123");
    }
}

package com.upweup.test.demo.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jcrenc
 * @since 2024/2/28 9:32
 */

@RestController
public class TestController {


    @RequestMapping("test")
    public void test() {
        System.out.println("test");
    }




}

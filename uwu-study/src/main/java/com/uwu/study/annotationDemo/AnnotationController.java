package com.uwu.study.annotationDemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/annotation")
public class AnnotationController {


    @TestAnnotation(value = {11111,22222})
    @RequestMapping("/login")
    @ResponseBody
    public String login() {
        System.out.println("--annotation---login .......");
        return "annotation登录";
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

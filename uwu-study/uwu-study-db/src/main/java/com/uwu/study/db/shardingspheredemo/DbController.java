package com.uwu.study.db.shardingspheredemo;

import com.uwu.study.db.shardingspheredemo.mapper.CustomerMapper;
import com.uwu.study.db.shardingspheredemo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DbController {

    @Resource
    private CustomerMapper userCustomerMapper;

    @Resource
    private UserService userService;


    @PostMapping("/db/createCustomerTable")
    public void createCustomerTable(){

        userCustomerMapper.createCustomerTable("customer_water");
    }

    @PostMapping("/db/swtest")
    public void swtest(){

        userService.createUser();
    }




}

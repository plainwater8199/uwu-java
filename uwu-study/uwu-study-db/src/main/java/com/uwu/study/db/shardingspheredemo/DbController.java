package com.uwu.study.db.shardingspheredemo;

import com.uwu.study.db.shardingspheredemo.mapper.CustomerMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DbController {

    @Resource
    private CustomerMapper userCustomerMapper;


    @PostMapping("/db/createCustomerTable")
    public void createCustomerTable(){
        userCustomerMapper.createCustomerTable("customer_water");
    }
}

package com.handwritten.demo.repository;

import com.handwritten.spring.annotation.Component;

/**
 * 用户仓库类，演示数据访问层
 */
@Component
public class UserRepository {
    
    public void save(String username) {
        System.out.println("UserRepository: 保存用户到数据库 - " + username);
        // 模拟数据库保存操作
    }
    
    public String findById(Long id) {
        System.out.println("UserRepository: 从数据库查询用户 ID=" + id);
        // 模拟数据库查询操作
        return "User_" + id;
    }
    
    public void printInfo() {
        System.out.println("UserRepository实例: " + this);
    }
}
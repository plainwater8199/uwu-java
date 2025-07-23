package com.handwritten.demo.service;

import com.handwritten.demo.repository.UserRepository;
import com.handwritten.spring.annotation.Autowired;
import com.handwritten.spring.annotation.Component;

/**
 * 用户服务类，演示Spring的组件扫描和依赖注入
 */
@Component
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public void saveUser(String username) {
        System.out.println("UserService: 准备保存用户 " + username);
        userRepository.save(username);
        System.out.println("UserService: 用户保存完成");
    }
    
    public String getUser(Long id) {
        System.out.println("UserService: 查询用户 ID=" + id);
        return userRepository.findById(id);
    }
    
    public void printInfo() {
        System.out.println("UserService实例: " + this);
        System.out.println("注入的UserRepository实例: " + userRepository);
    }
}
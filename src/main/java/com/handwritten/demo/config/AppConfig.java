package com.handwritten.demo.config;

import com.handwritten.spring.annotation.ComponentScan;

/**
 * 应用配置类
 * 使用@ComponentScan注解指定要扫描的包路径
 */
@ComponentScan(basePackages = {"com.handwritten.demo"})
public class AppConfig {
    
}
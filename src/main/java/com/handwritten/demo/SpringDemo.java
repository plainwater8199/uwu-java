package com.handwritten.demo;

import com.handwritten.demo.config.AppConfig;
import com.handwritten.demo.service.UserService;
import com.handwritten.demo.repository.UserRepository;
import com.handwritten.spring.context.AnnotationConfigApplicationContext;

/**
 * Spring框架演示类
 * 展示手写Spring框架的核心功能
 */
public class SpringDemo {
    
    public static void main(String[] args) {
        System.out.println("=== 手写Spring框架演示 ===");
        System.out.println();
        
        // 1. 创建Spring容器
        System.out.println("1. 创建Spring容器...");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println();
        
        // 2. 查看容器中的Bean
        System.out.println("2. 容器中的Bean列表:");
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println("  - " + beanName + " : " + context.getType(beanName).getName());
        }
        System.out.println();
        
        // 3. 获取Bean并测试功能
        System.out.println("3. 测试依赖注入和业务功能:");
        
        // 根据类型获取Bean
        UserService userService = context.getBean(UserService.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        
        // 验证依赖注入
        userService.printInfo();
        userRepository.printInfo();
        System.out.println();
        
        // 4. 测试业务逻辑
        System.out.println("4. 测试业务逻辑:");
        userService.saveUser("张三");
        System.out.println();
        
        String user = userService.getUser(1L);
        System.out.println("查询结果: " + user);
        System.out.println();
        
        // 5. 验证单例模式
        System.out.println("5. 验证单例模式:");
        UserService userService2 = context.getBean("userService", UserService.class);
        System.out.println("userService == userService2: " + (userService == userService2));
        System.out.println("userService: " + userService);
        System.out.println("userService2: " + userService2);
        System.out.println();
        
        // 6. 测试根据名称获取Bean
        System.out.println("6. 根据名称获取Bean:");
        Object userRepoByName = context.getBean("userRepository");
        System.out.println("根据名称获取的UserRepository: " + userRepoByName);
        System.out.println("类型匹配: " + (userRepoByName instanceof UserRepository));
        System.out.println();
        
        System.out.println("=== Spring框架演示完成 ===");
    }
}
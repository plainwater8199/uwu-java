package com.handwritten.spring.annotation;

import java.lang.annotation.*;

/**
 * 组件扫描注解，指定扫描包路径
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {
    /**
     * 扫描的包路径
     */
    String[] value() default {};
    
    /**
     * 扫描的基础包路径
     */
    String[] basePackages() default {};
}
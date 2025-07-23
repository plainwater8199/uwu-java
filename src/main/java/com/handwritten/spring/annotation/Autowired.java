package com.handwritten.spring.annotation;

import java.lang.annotation.*;

/**
 * 自动装配注解，用于依赖注入
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    /**
     * 是否必须，如果为true且找不到对应的bean则抛出异常
     */
    boolean required() default true;
}
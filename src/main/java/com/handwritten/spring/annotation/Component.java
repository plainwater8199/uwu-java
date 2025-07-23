package com.handwritten.spring.annotation;

import java.lang.annotation.*;

/**
 * 组件注解，标记需要被Spring容器管理的类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    /**
     * Bean的名称，默认为类名首字母小写
     */
    String value() default "";
}
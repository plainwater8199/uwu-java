package com.uwu.study.annotationDemo;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BossAuth {
    //必填参数
    String[] value() default {};
}

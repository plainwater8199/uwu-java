package com.uwu.study.annotationDemo;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
    //必填参数
    int[] value() default {};
}

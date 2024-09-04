package com.uwu.study.annotationDemo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class AnnotationAspectDemo {

    @Before("@annotation(TestAnnotation)")
    public void Intercept(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        // 根据接口名称获取该接口对应类上的指定注解
        if(clazz.isAnnotationPresent(TestAnnotation.class)){
            TestAnnotation classAnnotation = clazz.getAnnotation(TestAnnotation.class);
            String[] value = classAnnotation.value();
            for(Object item : value){
                System.out.println(item.toString());
            }
        }
        // 获取接口上的指定注解
        TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
        String[] value = annotation.value();
        for(Object item : value){
            System.out.println(item.toString());
            if("11111".equals(item.toString())){
                System.out.println("权限异常");
                break;
            }
        }

    }
}

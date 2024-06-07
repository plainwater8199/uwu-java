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

        TestAnnotation annotation = method.getAnnotation(TestAnnotation.class);
        int[] value = annotation.value();
        for(Object item : value){
            System.out.println(item.toString());
            if("11111".equals(item.toString())){
                throw new RuntimeException();
            }
        }

    }
}

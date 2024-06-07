package com.uwu.study.filterandInterceptor.interceptorDemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Resource
    private Interceptor1 interceptor1;

    @Resource
    private Interceptor2 interceptor2;


    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor1).addPathPatterns("/**").excludePathPatterns("/login","/register");
        registry.addInterceptor(interceptor2).addPathPatterns("/**").excludePathPatterns("/login","/register");
        WebMvcConfigurer.super.addInterceptors(registry);
        System.out.println("+++++++++++++++++++++++++  addInterceptors  +++++++++++++++++++++");
    }


    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}

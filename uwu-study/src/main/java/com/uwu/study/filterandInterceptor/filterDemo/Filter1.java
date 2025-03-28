package com.uwu.study.filterandInterceptor.filterDemo;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Order(1)
@Component
@Slf4j
@WebFilter(filterName = "Filter1",urlPatterns = "/**")
public class Filter1 implements Filter {
    private static Set<String> urls = new HashSet<>();

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Resource
    ApplicationContext applicationContext;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("-----------------Filter1 init--------------------");

        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        for(Map.Entry<RequestMappingInfo, HandlerMethod> entry: handlerMethods.entrySet()){
            RequestMappingInfo key = entry.getKey();
            if(key != null){
                PathPatternsRequestCondition pathPatternsCondition = key.getPathPatternsCondition();
                if(pathPatternsCondition!= null){
                    urls.addAll(pathPatternsCondition.getPatternValues());
                }
            }
        }

        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("-----------------Filter1 doFilter 前--------------------");
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
//        System.out.println("---------------"+requestURI);
        boolean isMatch = false;
        for(String urlPath : urls){
            if(pathMatcher.match(urlPath,requestURI)){
                filterChain.doFilter(servletRequest,servletResponse);
//                System.out.println("-----------------Filter1 doFilter 后--------------------");
                isMatch = true;
                break;
            }
        }
        if(!isMatch){
            System.out.println("++++++++++++++++++非法访问+++++++++++++++++++++++++++");
            returnJson(servletResponse, 9999, "非法访问");
        }
    }

    private void returnJson(ServletResponse response, int errorCode, String errorMessage) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("code", errorCode);
        jsonObject.set("msg", errorMessage);
        try (PrintWriter writer = response.getWriter()) {
            writer.print(jsonObject);
        } catch (IOException e) {
            log.error("response error", e);
        }
    }

    @Override
    public void destroy() {
//        System.out.println("-----------------Filter1 destroy--------------------");
        Filter.super.destroy();
    }
}

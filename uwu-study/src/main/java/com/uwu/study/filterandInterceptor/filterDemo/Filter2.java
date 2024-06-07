package com.uwu.study.filterandInterceptor.filterDemo;

import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Order(2)
@Component
@WebFilter(filterName = "Filter2",urlPatterns = "/**")
public class Filter2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("-----------------Filter2222222222 init--------------------");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("-----------------Filter2222222222 doFilter 前--------------------");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("-----------------Filter2222222222 doFilter 后--------------------");
    }

    @Override
    public void destroy() {
        System.out.println("-----------------Filter2222222222 destroy--------------------");
        Filter.super.destroy();
    }
}

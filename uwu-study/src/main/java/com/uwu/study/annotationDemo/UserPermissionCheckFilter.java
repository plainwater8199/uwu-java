package com.uwu.study.annotationDemo;
import cn.hutool.json.JSONObject;
import com.alibaba.nacos.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 管理平台分别管理几个不同的平台，而每位管理员都有属于自己的平台权限。
 * 该过滤器用于对用户的权限进行检查。
 */
@RestControllerAdvice
@Slf4j
@Order(5)
public class UserPermissionCheckFilter implements Filter {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = httpServletRequest.getHeader("token");
            if (httpServletRequest.getRequestURI().endsWith("/admin/user/login") || httpServletRequest.getRequestURI().endsWith("/admin/user/logout") || httpServletRequest.getRequestURI().endsWith("/admin/user/queryCurrentCommunityAdminBaseInfo") || httpServletRequest.getRequestURI().endsWith("/admin/user/queryAdminAuthList")) {
                chain.doFilter(request, response);
                return;
            }
            if (StringUtils.isNotEmpty(token)) {
                String uri = httpServletRequest.getHeader("5g-uri");
                String platform = httpServletRequest.getHeader("platform");
                if(StringUtils.isNotEmpty(platform) && StringUtils.isNotEmpty(uri)){
                    boolean checkURI = checkURIForInterface(httpServletRequest,uri);//校验uri是否被篡改
                    if (checkURI) {
                        log.info("---------------------platform-----------------------:" + platform);
//                        Map<String, List<String>> menuMap = redisService.getCacheObject("USER_ID:" + SessionContextUtil.getLoginUser().getUserId());
//                        log.info("menuMap {}", menuMap);
//                        if (null == menuMap || menuMap.isEmpty() || null == menuMap.get(platform) || !menuMap.get(platform).contains(uri)) {
//                            returnJson(response, 9999, "权限限制，不允许被访问");
//                            return;
//                        }
                    }else{
                        returnJson(response, 9999, "权限限制，不允许被访问");
                        return;
                    }
                }else{
                    returnJson(response, 9999, "权限限制，不允许被访问");
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // 传递异常信息
            request.setAttribute("filterError", e);
            // 指定处理该请求的处理器
            request.getRequestDispatcher(CommonConstant.ERROR_CONTROLLER_PATH).forward(request, response);
        }
    }

    private boolean checkURIForInterface(HttpServletRequest httpServletRequest, String uri) {
        // 通过 HandlerMapping 获取 HandlerMethod
        try {
            HandlerMethod handlerMethod  = (HandlerMethod) Objects.requireNonNull(requestMappingHandlerMapping.getHandler(httpServletRequest)).getHandler();
            boolean isTrue = false;
            BossAuth methodAnnotation = handlerMethod.getMethodAnnotation(BossAuth.class);
            if(methodAnnotation != null){
                String[] value = methodAnnotation.value();
                isTrue =Arrays.asList(value).contains(uri);
            }
            if(!isTrue){
                Method method = handlerMethod.getMethod();
                Class<?> clazz = method.getDeclaringClass();
                // 根据接口名称获取该接口对应类上的指定注解
                if(clazz.isAnnotationPresent(BossAuth.class)){
                    BossAuth classAnnotation = clazz.getAnnotation(BossAuth.class);
                    String[] value = classAnnotation.value();
                    isTrue =Arrays.asList(value).contains(uri);
                }
            }
            return isTrue;
        } catch (Exception e) {
            return true;
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
}

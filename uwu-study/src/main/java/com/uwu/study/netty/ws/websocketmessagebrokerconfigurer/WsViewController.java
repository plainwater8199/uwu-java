package com.uwu.study.netty.ws.websocketmessagebrokerconfigurer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @RestController = @Controller + @ResponseBody（所有方法默认返回 JSON/XML 数据）
 *
 * @Controller（默认返回视图名称，需配合模板引擎如 Thymeleaf）
 */
@Controller
public class WsViewController {
    /*
    在 resources/template/index.html 设计了调用界面，可以通过调用界面进行访问。 http://localhost:8889/
    返回视图
     */

    @GetMapping("/")
    public String index() {
        return "index";   // 对应 src/main/resources/templates/index.html
    }
}

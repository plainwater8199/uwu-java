package com.uwu.study.netty.ws.websocketmessagebrokerconfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


/**
 * @RestController = @Controller + @ResponseBody（所有方法默认返回 JSON/XML 数据）
 *
 * @Controller（默认返回视图名称，需配合模板引擎如 Thymeleaf）
 */
@RestController
public class WsTestController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 这里需要 websocket 被链接，（这里的案例就是服务启动，然后浏览器访问 http://localhost:8889/,会路径到/resources/templates/index.html）
     * 然后 ws 就被链接了，
     * 然后通过 postman 调用 /test-send 接口，就可以在浏览器中打印出通过接口发送的消息。
     *
     * SimpMessagingTemplate
     * 确保客户端已经通过WebSocket连接到服务器。客户端需要先建立WebSocket连接，然后才能接收消息。
     * @param message
     */


    @PostMapping("/test-send")
    public void testSend(@RequestBody Message message) {
        // 模拟 WebSocket 处理
        messagingTemplate.convertAndSend("/topic/messages",
                new Message(message.getContent(), "system"));
        System.out.println("调用完了！！！！！！！");
    }

    @GetMapping("/check-bean")
    public String checkBean() {
        if (messagingTemplate != null) {
            return "SimpMessagingTemplate 注入成功！";
        } else {
            return "SimpMessagingTemplate 未注入，请检查配置！";
        }
    }

}

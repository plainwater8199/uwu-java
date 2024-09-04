package com.uwu.study.rockemq;


import com.alibaba.nacos.api.model.v2.Result;
import com.uwu.study.rockemq.service.MQProducerService;
import com.uwu.study.rockemq.vo.User;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rocketmq/")
public class RocketMQController {

    @Autowired
    private MQProducerService mqProducerService;

    @GetMapping("/send")
    public void send() {
        User user = new User();
        user.setName("water");
        user.setAge(20);
        mqProducerService.send(user);
    }

    @GetMapping("/sendTag")
    public Result<SendResult> sendTag() {
        SendResult sendResult = mqProducerService.sendTagMsg("带有tag的字符消息");
        return Result.success(sendResult);
    }

}

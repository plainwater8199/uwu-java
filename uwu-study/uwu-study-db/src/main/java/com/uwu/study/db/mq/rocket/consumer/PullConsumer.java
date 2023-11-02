//package com.uwu.study.db.mq.rocket.consumer;
//
//import com.uwu.study.db.mq.rocket.template.ExtRocketMQTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//@Slf4j
//@Component
//public class PullConsumer implements CommandLineRunner {
//
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    @Autowired
//    private ExtRocketMQTemplate extRocketMQTemplate;
//
//    @Override
//    public void run(String... args) {
//        while (true) {
//
//            List<String> messages = rocketMQTemplate.receive(String.class, 5000);
//            log.info("receive from rocketMQTemplate, messages={}", messages);
//
//
//            messages = extRocketMQTemplate.receive(String.class, 5000);
//            log.info("receive from extRocketMQTemplate, messages={}", messages);
//        }
//    }
//}

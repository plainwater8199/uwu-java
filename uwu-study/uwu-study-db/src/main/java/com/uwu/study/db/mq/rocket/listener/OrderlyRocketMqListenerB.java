package com.uwu.study.db.mq.rocket.listener;

import com.uwu.study.db.mq.rocket.constant.TopicConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = TopicConstants.ORDERLY_ROCKETMQ_TOPIC_TEST,
        consumerGroup = TopicConstants.ORDERLY_ROCKETMQ_TOPIC_TEST + TopicConstants.CONSUMER_GROUP,
        accessKey = "${rocketmq.consumer.access-key}", secretKey = "${rocketmq.consumer.secret-key}",
        consumeMode = ConsumeMode.ORDERLY)
public class OrderlyRocketMqListenerB implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        log.info("顺序订阅-接收到的信息：{}", s);
    }
}

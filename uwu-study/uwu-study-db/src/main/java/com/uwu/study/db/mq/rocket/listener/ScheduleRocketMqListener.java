package com.uwu.study.db.mq.rocket.listener;

import com.uwu.study.db.mq.rocket.constant.TopicConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = TopicConstants.SCHEDULE_ROCKETMQ_TOPIC_TEST,
        consumerGroup = TopicConstants.SCHEDULE_ROCKETMQ_TOPIC_TEST + TopicConstants.CONSUMER_GROUP,
        accessKey = "${rocketmq.consumer.access-key}", secretKey = "${rocketmq.consumer.secret-key}")
public class ScheduleRocketMqListener implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        String msg = "内容：" + new String(message.getBody()) + "，时间：" + (System.currentTimeMillis() - message.getBornTimestamp()) + "ms later";
        log.info("延时订阅-接收到的信息：{}", msg);
        log.info("延时消息-接受时间：{}", System.currentTimeMillis());
    }
}
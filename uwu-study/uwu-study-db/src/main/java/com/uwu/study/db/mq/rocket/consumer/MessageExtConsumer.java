package com.uwu.study.db.mq.rocket.consumer;

import com.uwu.study.db.mq.rocket.constant.TopicConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = TopicConstants.NORMAL_ROCKETMQ_TOPIC_TEST, consumerGroup = TopicConstants.NORMAL_ROCKETMQ_TOPIC_TEST + TopicConstants.CONSUMER_GROUP, accessKey = "${rocketmq.consumer.access-key}", secretKey = "${rocketmq.consumer.secret-key}")
public class MessageExtConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(MessageExt message) {
        log.info("MessageExtConsumer received message, msgId: {}, body: {} ", message.getMsgId(), new String(message.getBody()));
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {

        // 设置从当前时间开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));


        // 设置最大重试次数，默认16次
        consumer.setMaxReconsumeTimes(5);

    }
}
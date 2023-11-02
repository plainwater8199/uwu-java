package com.uwu.study.db.mq.rocket.template;

import com.uwu.study.db.mq.rocket.constant.TopicConstants;
import org.apache.rocketmq.spring.annotation.ExtRocketMQConsumerConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * 可用于不同name-server或者其他特定的属性来定义非标的RocketMQTemplate，此示例定义消息Topic和消费者
 */
@ExtRocketMQConsumerConfiguration(group = TopicConstants.EXT_ROCKETMQ_TOPIC_TEST + TopicConstants.CONSUMER_GROUP,
        topic = TopicConstants.EXT_ROCKETMQ_TOPIC_TEST)
public class ExtRocketMQTemplate extends RocketMQTemplate {
}

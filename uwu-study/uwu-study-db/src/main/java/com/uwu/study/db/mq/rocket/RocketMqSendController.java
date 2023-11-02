package com.uwu.study.db.mq.rocket;


import com.uwu.study.db.mq.rocket.constant.TopicConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 *  https://blog.csdn.net/ctwy291314/article/details/129755565
 */
@Slf4j
@RestController
public class RocketMqSendController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 向rocketmq发送同步和异步消息
     */
    @RequestMapping(value = "sendNormal", method = RequestMethod.GET)
    public String sendNormal() {
        rocketMQTemplate.send(TopicConstants.NORMAL_ROCKETMQ_TOPIC_TEST + ":sync", MessageBuilder.withPayload("同步发送消息").build());
        rocketMQTemplate.asyncSend(TopicConstants.NORMAL_ROCKETMQ_TOPIC_TEST + ":async", MessageBuilder.withPayload("异步发送消息").build(), new SendCallback() {

            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步发送成功:{}", sendResult.getSendStatus().name());
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("异步发送失败:{}", throwable.getMessage());
            }
        });
        return "OK";
    }

    /**
     * 向rockertmq发送顺序消息，同步方式
     *
     * @return
     */
    @RequestMapping(value = "sendOrderlySync", method = RequestMethod.GET)
    public String sendOrderlySync() {
        // 订单列表
        List<OrderStep> orderList = buildOrders();
        for (int i = 0; i < 10; i++) {
            Message msg = MessageBuilder.withPayload(orderList.get(i).toString()).build();
            String orderId = String.valueOf(orderList.get(i).getOrderId());
            rocketMQTemplate.sendOneWayOrderly(TopicConstants.ORDERLY_ROCKETMQ_TOPIC_TEST + ":sync", msg, orderId);
        }
        return "OK";
    }

    /**
     * rockertmq发送顺序消息，异步方式
     *
     * @return
     */
    @RequestMapping(value = "sendOrderlyAsync", method = RequestMethod.GET)
    public String sendOrderlyAsync() {
        // 订单列表
        List<OrderStep> orderList = buildOrders();
        for (int i = 0; i < 10; i++) {
            Message msg = MessageBuilder.withPayload(orderList.get(i).toString()).build();
            String orderId = String.valueOf(orderList.get(i).getOrderId());
            rocketMQTemplate.syncSendOrderly(TopicConstants.ORDERLY_ROCKETMQ_TOPIC_TEST + ":async", msg, orderId);
        }
        return "OK";
    }

    /**
     * rockertmq发送延时消息
     *
     * @return
     */
    @RequestMapping(value = "sendSchedule", method = RequestMethod.GET)
    public String sendSchedule() {
        Message msg = MessageBuilder.withPayload("延时消息")
                .build();
        rocketMQTemplate.syncSendDelayTimeSeconds(TopicConstants.SCHEDULE_ROCKETMQ_TOPIC_TEST + ":", msg, 20);
        log.info("延时消息-发布时间：{}", System.currentTimeMillis());
        return "OK";
    }

    /**
     * rockertmq发送生产端事务消息
     *
     * @return
     */
    @RequestMapping(value = "sendTransaction", method = RequestMethod.GET)
    public String sendTransaction() {
        Message msg = MessageBuilder.withPayload("事务消息")
                .build();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(TopicConstants.TRANSACTION_ROCKETMQ_TOPIC_TEST + ":", msg, "自定义参数");
        log.info("事务消息-发布结果：{} = {}", result.getTransactionId(), result.getSendStatus());
        return "OK";
    }

    /**
     * 向ockertmq 消费端pull模式发生消息
     *
     * @return
     */
    @RequestMapping(value = "sendPull", method = RequestMethod.GET)
    public String pull() {
        for (int i = 0; i < 10; i++) {
            Message msg = MessageBuilder.withPayload("pull 消息" + i).build();
            rocketMQTemplate.syncSend(TopicConstants.PULL_ROCKETMQ_TOPIC_TEST + ":", msg);
        }
        for (int i = 0; i < 10; i++) {
            Message msg = MessageBuilder.withPayload("pull ext 消息" + i).build();
            rocketMQTemplate.syncSend(TopicConstants.EXT_ROCKETMQ_TOPIC_TEST + ":", msg);
        }
        return "OK";
    }

    /**
     * 订单的步骤
     */
    private static class OrderStep {
        private long orderId;
        private String desc;

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "OrderStep{" + "orderId=" + orderId + ", desc='" + desc + '\'' + '}';
        }
    }

    /**
     * 生成模拟订单数据
     */
    private List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();

        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }
}

package com.uwu.study.db.mq.rocket.listener;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RocketMQTransactionListener
class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    // 事务消息共有三种状态，提交状态、回滚状态、中间状态：

    // RocketMQLocalTransactionState.COMMIT: 提交事务，它允许消费者消费此消息。
    // RocketMQLocalTransactionState.ROLLBACK: 回滚事务，它代表该消息将被删除，不允许被消费。
    // RocketMQLocalTransactionState.UNKNOWN: 中间状态，它代表需要检查消息队列来确定状态。

    // executeLocalTransaction 方法来执行本地事务
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String transactionId = msg.getHeaders().get("__transactionId__").toString();
        log.info("执行本地事务，transactionId：{}", transactionId);

        int value = transactionIndex.getAndIncrement();
        int status = value % 3;
        localTrans.put(transactionId, status);
        log.info("获取自定义参数：{}", arg);
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    // checkLocalTransaction 方法用于检查本地事务状态，并回应消息队列的检查请求
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String transactionId = msg.getHeaders().get("__transactionId__").toString();
        log.info("检查本地事务状态，transactionId：{}", transactionId);
        Integer status = localTrans.get(transactionId);
        if (null != status) {
            switch (status) {
                case 0:
                    return RocketMQLocalTransactionState.UNKNOWN;
                case 1:
                    return RocketMQLocalTransactionState.COMMIT;
                case 2:
                    return RocketMQLocalTransactionState.ROLLBACK;
            }
        }
        return RocketMQLocalTransactionState.COMMIT;
    }
}

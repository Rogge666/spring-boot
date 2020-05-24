package com.rogge.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2020-05-23.
 * @since 1.0.0
 */
//@Configuration
@Slf4j
public class PullConsumerListener {

    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<>();

    @Value("${apache.rocketmq.consumer.consumerGroup}")
    private String consumerGroup;

    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Bean
    public DefaultMQPullConsumer defaultMQPullConsumer() {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(consumerGroup);

        try {
            consumer.start();
            log.info("[PullConsumer 已启动]");
            Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(RocketMqConfig.TOPIC_TEST1);
            for (MessageQueue mq : mqs) {
                log.info("PullConsume from the queue: %s%n", mq);
                SINGLE_MQ:
                while (true) {
                    try {
                        PullResult pullResult =
                                consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                        log.info("%s%n", pullResult);
                        putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                        switch (pullResult.getPullStatus()) {
                            case FOUND:
                                break;
                            case NO_MATCHED_MSG:
                                break;
                            case NO_NEW_MSG:
                                break SINGLE_MQ;
                            case OFFSET_ILLEGAL:
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        consumer.shutdown();
        return consumer;
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSE_TABLE.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        OFFSE_TABLE.put(mq, offset);
    }

}

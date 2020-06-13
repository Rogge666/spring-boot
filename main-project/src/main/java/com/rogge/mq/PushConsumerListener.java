package com.rogge.mq;

import com.rogge.common.model.User;
import com.rogge.common.util.ProtoBufUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
@Configuration
@Slf4j
public class PushConsumerListener {

    @Value("${apache.rocketmq.consumer.consumerGroup1}")
    private String consumerGroup;

    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            //广播模式消费
//            consumer.setMessageModel(MessageModel.BROADCASTING);
            consumer.subscribe(RocketMqConfig.TOPIC_TEST1, "*");

            // 如果是第一次启动，从队列头部开始消费
            // 如果不是第一次启动，从上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.setMaxReconsumeTimes(2);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                    try {
                        for (MessageExt messageExt : list) {
                            if (RocketMqConfig.TAG_ORDER.equals(messageExt.getTags())) {
                                // 参数解析，反序列化解析参数
                                User lUser = ProtoBufUtil.deserializer(messageExt.getBody(), User.class);
                                if (lUser != null) {
//                                    if (lUser.getId().equals("1")) {
//                                        throw new Exception(lUser.toString());
//                                    }
                                    log.info("[PushConsumer] msgID({}) msgBody : {}", messageExt.getMsgId(), lUser.toString());
                                } else {
                                    log.error("[PushConsumer] msgID({}) msgBody : 为空", messageExt.getMsgId());
                                }
                            }
//                            String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
//                            System.out.println("[PushConsumer] msgID(" + messageExt.getMsgId() + ") msgBody : " + messageBody);
//                            System.out.println("[PushConsumer] MessageExt(" + messageExt.toString() + ")");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            log.info("[PushConsumer 已启动]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return consumer;
    }
}

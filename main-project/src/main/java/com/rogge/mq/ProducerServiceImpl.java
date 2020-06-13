package com.rogge.mq;

import com.rogge.common.util.ProtoBufUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
@Service
@Slf4j
public class ProducerServiceImpl {

    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;

    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    private DefaultMQProducer producer;

    @PostConstruct
    public void initProducer() {
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        //设置发送消息时重试次数
        producer.setRetryTimesWhenSendFailed(3);
        try {
            producer.start();
            log.info("[Producer 已启动]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> String send(String topic, String tags, T msg) {
        SendResult result = null;
        try {
            Message message = new Message(topic, tags, ProtoBufUtil.serializer(msg));
            //设置发送延迟消息等级
            //level  1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(3);//设置延迟1m消费
            result = producer.send(message);
            log.info("[Producer] msgID(" + result.getMsgId() + ") " + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"MsgId\":\"" + result.getMsgId() + "\"}";
    }

    @PreDestroy
    public void shutDownProducer() {
        if (producer != null) {
            producer.shutdown();
        }
    }
}

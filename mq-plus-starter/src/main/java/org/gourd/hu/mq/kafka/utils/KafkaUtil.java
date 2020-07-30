package org.gourd.hu.mq.kafka.utils;

import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.holder.SpringContextHolder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @Description kafka消息发送工具类
 * @Author gourd.hu
 * @Date 2020/7/28 13:56
 * @Version 1.0
 */
@Slf4j
public class KafkaUtil {

    /**
     * 发送topic消息
     * @param topic
     * @param object
     */
    public static void sendTopicMessage(String topic, Object object){
        log.info("发送topic消息体：{}",object);
        KafkaTemplate<String,Object> kafkaTemplate = SpringContextHolder.getBean(KafkaTemplate.class);
        ListenableFuture listenableFuture = kafkaTemplate.send(topic,object);
        listenableFuture.addCallback(
                o -> log.info("消息发送成功,{}", o.toString()),
                throwable -> log.info("消息发送失败,{}" + throwable.getMessage())
        );
    }

}

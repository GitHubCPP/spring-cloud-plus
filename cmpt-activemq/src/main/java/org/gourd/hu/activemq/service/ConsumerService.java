package org.gourd.hu.activemq.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;

/**
 * 消息消费
 *
 * @author gourd.hu
 */
@Component
@Slf4j
public class ConsumerService {


    /**
     * 使用JmsListener配置消费者监听的队列，其中name是接收到的消息
     * @param message
     * @return
     */
    @JmsListener(destination = "${spring.activemq.queue-name:gourd-queue}",containerFactory = "jmsListenerContainerQueue")
    @SendTo("SQueue")
    public void handleMessage(final Object message, Session session) throws JMSException {
        ActiveMQObjectMessage activeMQObjectMessage = (ActiveMQObjectMessage) message;
        activeMQObjectMessage.setTrustAllPackages(true);
        log.info("queue-consumer收到的报文为:" + JSON.toJSONString(activeMQObjectMessage.getObject()));
        if(true){
            session.close();
        }else {
            session.recover();
        }
    }

    @JmsListener(destination = "${spring.activemq.topic-name:gourd-topic}",containerFactory = "jmsListenerContainerTopic")
    @SendTo("STopic")
    public void receiveTopic(final Object message, Session session) throws JMSException {
        ActiveMQObjectMessage activeMQObjectMessage = (ActiveMQObjectMessage) message;
        activeMQObjectMessage.setTrustAllPackages(true);
        log.info("topic-consumer收到的报文为:" + JSON.toJSONString(activeMQObjectMessage.getObject()));
        if(true){
            session.close();
        }else {
            session.recover();
        }
//        if(true){
//            throw new BusinessException("测试消息重试");
//        }
    }
 
}
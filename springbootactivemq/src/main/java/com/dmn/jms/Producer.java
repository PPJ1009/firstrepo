package com.dmn.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@Slf4j
@Component
public class Producer {
    @Resource(name = "springboot.queue")
    private Queue queue;

    @Resource(name = "springboot.topic")
    private Topic topic;

    @Resource(name = "springboot.replyQueue")
    private Queue replyQueue;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 发送消息
     * @param destination 发送到的目标队列
     * @param message 待发送的消息内容
     */
    public void sendMessage(Destination destination,final String message){
        jmsMessagingTemplate.convertAndSend(destination,message);
    }

    /**
     * 发送队列消息
     * @param message
     */
    public void sendQueueMessage(final String message){
        sendMessage(queue,message);
    }

    /**
     * 发送Topic消息
     * @param message
     */
    public void sendTopicMessage(final String message){
        sendMessage(topic,message);
    }

    /**
     * 发送队列的回复消息
     * @param message
     */
    public void sendQueueMessageReply(String message){
        sendMessage(replyQueue,message);
    }

    /**
     * 生产者监听消费者的应答消息
     * @param text
     */
    @JmsListener(destination = "replyTo.queue",containerFactory = "jmsQueueListenerContainerFactory")
    public void consumerMessage(final String text){
        log.warn("从replyTo.queue队列中收到的应答报文为："+text);
    }

}

package com.dmn.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {
    /**
     * 监听Queue队列 queue类型
     */
    @JmsListener(destination = "springboot.queue",containerFactory = "jmsQueueListenerContainerFactory")
    public void receiveQueue(String text){
        log.warn(this.getClass().getName()+"-->收到的报文为："+text);
    }

    /**
     * 监听Topic队列 topic类型
     */
    @JmsListener(destination = "springboot.topic",containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveTopic(String text){
        log.warn(this.getClass().getName()+"-->收到的报文为："+text);
    }

    /**
     * 回复给生产者的应答消息
     */
    @JmsListener(destination = "springboot.replyQueue",containerFactory = "jmsQueueListenerContainerFactory")
    @SendTo("replyTo.queue")//消费者应答后通知生产者
    public String receiveQueueReply(String text){
        log.warn(this.getClass().getName()+"-->收到的报文为："+text);
        return "回复的信息为-->"+text;
    }
}

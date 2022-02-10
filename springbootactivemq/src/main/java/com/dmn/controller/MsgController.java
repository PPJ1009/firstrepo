package com.dmn.controller;

import com.dmn.entity.User;
import com.dmn.jms.Consumer;
import com.dmn.jms.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {
    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;

    @GetMapping("/sendQueue")
    public String sendQueueMsg(){
        User user = new User();
        user.setId(1L);
        user.setUsername("dmnQueue");
        user.setPassword("123");
        producer.sendQueueMessage(user.toString());
        return "发送成功！";
    }

    @GetMapping("/sendTopic")
    public String sendTopicMsg(){
        User user = new User();
        user.setId(2L);
        user.setUsername("dmnTopic");
        user.setPassword("123");
        producer.sendTopicMessage(user.toString());
        return "发送成功！";
    }

    @GetMapping("/reply")
    public String reply(){
        consumer.receiveQueueReply("回复回复");
        return "回复成功！";
    }
}

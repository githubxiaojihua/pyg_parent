package com.pyg.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试发送短信服务
 */

@RestController
public class QueueController {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    Destination queueDestination;

    @RequestMapping("/sendSms")
    public void testSendSms(){
        Map<String,Object> map = new HashMap<>();
        map.put("mobile", "13900001111");
        map.put("template_code", "SMS_85735065");
        map.put("sign_name", "黑马");
        map.put("param", "{\"number\":\"102931\"}");
        jmsTemplate.convertAndSend(queueDestination,map);
    }

}

package cn.itecast.demo.controller;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloworldController {

    @Autowired
    private Environment env;

    // 虽然这里显示的可能会议红色波浪线，但是不影响，还是会自动注入的因为在
    // ActiveMQAutoConfiguration中AutoConfigureBefore注解中已经进行了@Bean的声明了所以可以直接使用
    // ActiveMQAutoConfiguration是在spring-boot-autoconfigure包中的
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private ActiveMQTopic topic;

    @RequestMapping("/info")
    public String info(){
        return "helloworld---" + env.getProperty("url");
    }

    // 默认是点对点的模式发送
    @RequestMapping("/jms")
    public void sendMesage(String text){
        jmsMessagingTemplate.convertAndSend("itcast",text);
    }

    // 发布和订阅模式
    @RequestMapping("/ps")
    public void sendMessageByPs(String text){
        jmsMessagingTemplate.convertAndSend("myTopic",text);
    }

    @RequestMapping("/sendMap")
    public void sendMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("telephone","18955666666");
        map.put("content","恭喜你获得500万大奖");
        jmsMessagingTemplate.convertAndSend("itcast_map",map);
    }





}

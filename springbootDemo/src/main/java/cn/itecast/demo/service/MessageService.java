package cn.itecast.demo.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    @JmsListener(destination = "itcast")
    public void receviMessage(String text){
        System.out.println(text);
    }

    // 监听消息空间
    @JmsListener(destination="myTopic")
    public void receiveMessageByTopic(String text) {
        System.out.println("接受消息:" + text);
    }

}

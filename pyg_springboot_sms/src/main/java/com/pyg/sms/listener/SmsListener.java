package com.pyg.sms.listener;

import com.pyg.sms.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsListener {

    @Autowired
    private SmsUtils utils;

    @JmsListener(destination = "sms")
    public void sendMsg(Map<String,Object> map){
        System.out.println("消息监听器已经成功接收到消息：");
        System.out.println("模拟调用了SmsUtils工具发送短信，并成功发送");
        System.out.println("以下是发送的信息数据：");
        System.out.println("短信内容:" + map.get("number"));
        System.out.println("电话号码:" + map.get("mobile"));
        System.out.println("模板号码:" + map.get("template_code"));
        System.out.println("签名:" + map.get("signName"));

    }
}

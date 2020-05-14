package com.pyg.sms.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Destination;

@Configuration
public class DestinationConfig {

    @Bean
    public Destination getQueueDest(){
        return new ActiveMQQueue("sms");
    }
}

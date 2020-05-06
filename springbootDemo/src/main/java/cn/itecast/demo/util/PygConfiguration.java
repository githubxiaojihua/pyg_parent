package cn.itecast.demo.util;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PygConfiguration {

    //在spring配置文件中有多个bean
    //<bean class="对象">
    @Bean
    public ActiveMQTopic getTopic(){
        ActiveMQTopic topic = new ActiveMQTopic("myTopic");
        return topic;
    }
}

package com.xiaojihua.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * 订阅发布模式：
 * 一条消息可以被多个消费者接收，接收后消息不消失
 * 注意消费者要在生产者发送消息之前就启动否则接收不到消息
 */
public class C02ActiveMqTestDemo {

    /**
     * 消息生产者
     * @throws JMSException
     */
    @Test
    public void test01() throws JMSException {
        // 1、创建jms ConnectionFactory
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
        // 2、获取链接
        Connection connection = factory.createConnection();
        // 3、启动链接
        connection.start();
        // 4、获取session，第一个参数是是否启用事务如果true则需要session.commit()，第二个参数是消息确认模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5、创建主题
        Topic topic = session.createTopic("test-topic");

        // 6、创建消息生产者
        MessageProducer producer = session.createProducer(topic);
        // 7、创建消息类型
        TextMessage textMessage = session.createTextMessage("欢迎来到神奇的分布式系统");
        // 8、发送消息
        producer.send(textMessage);
        // 9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 消息消费者
     * @throws JMSException
     * @throws IOException
     */
    @Test
    public void test2() throws JMSException, IOException {
        // 1、创建jms 链接工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
        // 2、创建链接
        Connection connection = factory.createConnection();
        // 3、开启链接
        connection.start();
        // 4、获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5、创建主题
        Topic topic = session.createTopic("test-topic");
        // 6、创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        // 7、监听消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("接收到的消息为：" + ((TextMessage) message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 8、等待输入
        System.in.read();
        // 9、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

}

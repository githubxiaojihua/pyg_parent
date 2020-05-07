package com.pyg.springjms;

import com.pyg.demo.C01QueueProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-jms-producer.xml")
public class C01TestQueue {

    @Autowired
    private C01QueueProducer producer;

    @Test
    public void text(){
        producer.sendTextMessage("Springjms-点对点");
    }
}

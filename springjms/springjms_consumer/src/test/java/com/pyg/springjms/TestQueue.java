package com.pyg.springjms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-jms-consumer.xml")
public class TestQueue {

    // 这里只是定义了read，用于使程序一直可以监听消息
    // 具体干活的是Listener
    @Test
    public void text() throws IOException {
        System.in.read();
    }
}

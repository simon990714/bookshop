package com.czy.bookshop;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class RabbitMQTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void send(){
        rabbitTemplate.convertAndSend("simpleQueue","1231312");
    }
    @Test
    void receive(){
        System.out.println(rabbitTemplate.receiveAndConvert("simpleQueue"));
    }

    @Test
    void fanoutTest(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        //消息可以是任意类，只要监听的时候取出该类
        rabbitTemplate.convertAndSend("fanout_exchange",null,map);
    }


}

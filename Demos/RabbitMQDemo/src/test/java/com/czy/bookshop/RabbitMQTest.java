package com.czy.bookshop;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @Test
    void directTest(){
        User user1 = new User("张三","123",new Date());
        User user2 = new User("李四","321",new Date());
        //消息只能是String, byte[] and Serializable payloads，只要监听的时候取出该类
        rabbitTemplate.convertAndSend("direct_exchange","insert",JSONUtils.createJson(user1));
        rabbitTemplate.convertAndSend("direct_exchange","delete",JSONUtils.createJson(user2));
        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

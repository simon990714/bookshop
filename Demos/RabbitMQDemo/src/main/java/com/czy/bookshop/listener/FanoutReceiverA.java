package com.czy.bookshop.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
@RabbitListener(queues = "fanout-A")
public class FanoutReceiverA {
    @RabbitHandler
    public void process(Map testMsg){
        System.out.println(testMsg);
    }
}

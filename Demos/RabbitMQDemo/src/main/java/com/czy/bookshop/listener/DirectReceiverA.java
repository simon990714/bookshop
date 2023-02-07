package com.czy.bookshop.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "direct_queue-A")
public class DirectReceiverA {
    @RabbitHandler
    public void process(String json){
        System.out.println(json);
    }
}

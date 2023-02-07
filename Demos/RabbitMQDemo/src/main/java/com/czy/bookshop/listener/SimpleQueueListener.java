package com.czy.bookshop.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "simpleQueue")
public class SimpleQueueListener {
    @RabbitHandler
    public void onMsg(String msg){
        System.out.println(msg);
    }
}

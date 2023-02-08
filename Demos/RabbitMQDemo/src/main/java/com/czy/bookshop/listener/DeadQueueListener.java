package com.czy.bookshop.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RabbitListener(queues = "deadLetterQueue")
public class DeadQueueListener {
    @RabbitHandler
    public void process(String json){
        System.out.println("死信队列接收到消息"+json);
        System.out.println("接受时间" + new Date());
    }
}

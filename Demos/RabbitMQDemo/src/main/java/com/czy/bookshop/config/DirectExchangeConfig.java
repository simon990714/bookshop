package com.czy.bookshop.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {
    String deadLetterQueue = "deadLetterQueue";
    String deadLetterExchange = "deadLetterExchange";
    String deadRoutingKey = "deadRoutingKey";

    String normalQueue = "normalQueue";
    String normalExchange = "normalExchange";
    String normalKey = "normalKey";

    //死信交换机 和 死信队列
    @Bean
    public Queue deadLetterQueue(){
        return new Queue(deadLetterQueue);
    }

    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    public Binding bindingDeadLetterQueue(){
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadRoutingKey);
    }

    //业务交换机 和 业务队列

    //延迟队列
    @Bean
    public Queue normalQueue(){
        return QueueBuilder.durable(normalQueue)
                .deadLetterExchange(deadLetterExchange)
                .deadLetterRoutingKey(deadRoutingKey)//绑定死信交换机和死信路由
                .ttl(10*1000)
                .build();
    }

    @Bean
    public DirectExchange normalExchange(){
        return new DirectExchange(normalExchange);
    }

    @Bean
    public Binding bindingNormalQueue(){
        return BindingBuilder.bind(normalQueue()).to(normalExchange()).with(normalKey);
    }


}

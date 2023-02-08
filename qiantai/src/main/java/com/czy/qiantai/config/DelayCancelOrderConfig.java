package com.czy.qiantai.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelayCancelOrderConfig {
    String delayQueue = "delay_queue";
    String delayExchange = "delay_exchange";
    String delayRouting = "confirm.order";

    String deadOrderQueue = "dead_order_queue";
    String deadOrderExchange = "dead_order_exchange";
    String deadOrderRouting = "dead.order";

    //延时
    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable(delayQueue)
                .deadLetterRoutingKey(deadOrderRouting)
                .deadLetterExchange(deadOrderExchange)
                .ttl(15*60*1000)
                .build();
    }

    @Bean
    public DirectExchange delayExchange(){
        return new DirectExchange(delayExchange);
    }

    @Bean
    Binding bindingDelayQueue(){
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(delayRouting);
    }

    //死信
    @Bean
    public Queue deadOrderQueue(){
        return new Queue(deadOrderQueue);
    }

    @Bean
    public DirectExchange deadOrderExchange(){
        return new DirectExchange(deadOrderExchange);
    }

    @Bean
    Binding bindingDeadOrderQueue(){
        return BindingBuilder.bind(deadOrderQueue()).to(deadOrderExchange()).with(deadOrderRouting);
    }
}

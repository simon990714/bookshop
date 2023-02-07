package com.czy.bookshop.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {
    public static final String routingKeyA = "insert";
    public static final String routingKeyB = "delete";

    @Bean
    public Queue directQueueA(){
        return new Queue("direct_queue-A");
    }
    @Bean
    public Queue directQueueB(){
        return new Queue("direct_queue-B");
    }

    @Bean
    DirectExchange directExchange(){
        return new  DirectExchange("direct_exchange");
    }

    @Bean
    Binding bindingDirectQueueA(){
        return BindingBuilder.bind(directQueueA()).to(directExchange()).with(routingKeyA);
    }
    @Bean
    Binding bindingDirectQueueB(){
        return BindingBuilder.bind(directQueueB()).to(directExchange()).with(routingKeyB);
    }


}

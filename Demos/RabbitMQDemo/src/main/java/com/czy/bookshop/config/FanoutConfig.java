package com.czy.bookshop.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    @Bean
    public Queue queueA(){
        return new Queue("fanout-A");
    }
    @Bean
    public Queue queueB(){
        return new Queue("fanout-B");
    }
    @Bean
    public Queue queueC(){
        return new Queue("fanout-C");
    }

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout_exchange");
    }
    
    @Bean
    Binding bindingQueueA(){
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    Binding bindingQueueB(){
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    Binding bindingQueueC(){
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }

}

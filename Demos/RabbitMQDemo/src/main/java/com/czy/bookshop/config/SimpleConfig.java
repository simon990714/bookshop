package com.czy.bookshop.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SimpleConfig {
    @Bean//实例化一个简单队列
    public Queue simpleQueue(){
        return new Queue("simpleQueue");
    }
}

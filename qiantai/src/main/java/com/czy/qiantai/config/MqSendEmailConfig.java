package com.czy.qiantai.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqSendEmailConfig {
    @Bean
    public DirectExchange sendEmailExchange(){
        return new DirectExchange("sendEmailExchange");
    }
    @Bean
    public Queue sendEmailQueue(){
        return new Queue("sendEmailQueue");
    }
    @Bean
    public Binding sendEmailBind(){
        return BindingBuilder.bind(sendEmailQueue()).to(sendEmailExchange()).with("sendEmail");
    }


}

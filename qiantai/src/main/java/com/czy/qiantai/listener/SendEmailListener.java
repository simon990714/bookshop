package com.czy.qiantai.listener;

import com.google.code.kaptcha.Producer;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Component
public class SendEmailListener {
    @Autowired
    private Producer producer;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = "sendEmailQueue")
    public void onMsg(String email, Channel channel, Message message) throws IOException {
        System.out.println();
        //发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("1107064862@qq.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("蜗牛书店验证码");
        String code = producer.createText();
        simpleMailMessage.setText("您的注册验证码,请尽快使用 "+code);
        javaMailSender.send(simpleMailMessage);
        //缓存验证码
        stringRedisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);

        //手动ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }


}

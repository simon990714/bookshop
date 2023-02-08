package com.czy.bookshop.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "direct_queue-B")
public class DirectListener {
    @RabbitHandler

    public void process(String msg , Channel channel , Message message) throws IOException {
        try{
            int i = 1/0;
            System.out.println("接受到消息："+msg);
            System.out.println(message);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);


        }catch (Exception e){
            if (message.getMessageProperties().getRedelivered()){
                System.out.println("消息已处理,请勿重复处理！");
                // 拒绝消息
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);

            }else {
                //记录日志
                System.out.println("消息消费失败处理："+e.getMessage())    ;
                //第一个参数为消息的index，第二个参数是是否批量处理，第三个参数为是否让被拒绝的消息重新入队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }
        }
    }
}

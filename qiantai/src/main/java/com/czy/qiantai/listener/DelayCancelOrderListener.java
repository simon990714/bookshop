package com.czy.qiantai.listener;

import com.czy.qiantai.entity.Order;
import com.czy.qiantai.mapper.OrderMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RabbitListener(queues = "dead_order_queue")
public class DelayCancelOrderListener {
    @Autowired
    private OrderMapper orderMapper;
    @RabbitHandler
    public void process(Long orderId, Channel channel, Message message) throws IOException {
        Order order = orderMapper.selectById(orderId);
        if (order == null){
            System.out.println("订单异常");
        }
        if (order.getState() == 1){
            order.setState(5);
            orderMapper.updateById(order);
            System.out.println("订单未支付，已取消，取消时间：" + new Date());
        }else{
            System.out.println("订单已支付，无需操作");
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}

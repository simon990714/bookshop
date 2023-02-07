package com.czy.bookshop.dead;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class DeadDeclare {
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();


        //死信
        channel.exchangeDeclare(MqConst.DEAD_EXCHANGE, BuiltinExchangeType.DIRECT,true);

        channel.queueDeclare(MqConst.DEAD_QUEUE,true,false,false,null);

        channel.queueBind(MqConst.DEAD_QUEUE,MqConst.DEAD_EXCHANGE,MqConst.DeadLetterRoutingKey);




        //normal部分
        channel.exchangeDeclare(MqConst.NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        //延时队列
        HashMap<String, Object> map = new HashMap<>();//配置内容
        map.put("x-message-ttl",20*1000);//20s
        map.put("x-dead-letter-exchange",MqConst.DEAD_EXCHANGE);
        map.put("x-dead-letter-routing-key",MqConst.DeadLetterRoutingKey);
        channel.queueDeclare(MqConst.NORMAL_QUEUE,true,false,false,map);

        channel.queueBind(MqConst.NORMAL_QUEUE,MqConst.NORMAL_EXCHANGE,MqConst.ROUTING_KEY);



        //死信消费者监听
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("死信队列接收到消息" + new String(body));
                System.out.println("当前时间" + new Date());
                channel.basicAck(envelope.getDeliveryTag(),true);
            }
        };

        //死信队列开启监听
        channel.basicConsume(MqConst.DEAD_QUEUE,false,consumer);

    }
}

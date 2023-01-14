package com.czy.bookshop.topic;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.*;

import java.io.IOException;

public class TopicConsumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(MqConst.TOPIC_QUEUE2,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println(envelope.getRoutingKey() + "    2号接收到消息    " + msg);
//                处理相关数据

                channel.basicAck(envelope.getDeliveryTag(), false); //multiple为false时,只确认当前消息

            }
        });
    }
}

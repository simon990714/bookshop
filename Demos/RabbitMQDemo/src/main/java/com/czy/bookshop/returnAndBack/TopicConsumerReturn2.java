package com.czy.bookshop.returnAndBack;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicConsumerReturn2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();



        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(envelope.getRoutingKey() + new String(body));
                channel.basicAck(envelope.getDeliveryTag(),true);
            }
        };


        channel.basicConsume(MqConst.RETURN_QUEUE2,false,consumer);
    }
}

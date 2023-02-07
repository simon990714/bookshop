package com.czy.bookshop.returnAndBack;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicConsumerBack {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(MqConst.BACK_EXCHANGE, BuiltinExchangeType.FANOUT,true);

        channel.queueDeclare(MqConst.BACK_QUEUE,true,false,false,null);

        channel.queueBind(MqConst.BACK_QUEUE,MqConst.BACK_EXCHANGE,"");


        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("备用队列接收到消息：" + envelope.getRoutingKey() + new String(body));
                channel.basicAck(envelope.getDeliveryTag(),true);
            }
        };


        channel.basicConsume(MqConst.BACK_QUEUE,false,consumer);
    }
}

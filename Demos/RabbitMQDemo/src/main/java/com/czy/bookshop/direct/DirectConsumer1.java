package com.czy.bookshop.direct;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectConsumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(MqConst.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);

        //声明队列
        channel.queueDeclare(MqConst.DIRECT_QUEUE1,false,false,false,null);

        //绑定交换机
        channel.queueBind(MqConst.DIRECT_QUEUE1,MqConst.DIRECT_EXCHANGE,"error");


        //定义队列消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println(consumerTag);
                System.out.println(envelope);



                String msg = new String(body);
                System.out.println(" [错误]  : " + msg + "!");

                //手动ack
                channel.basicAck(envelope.getDeliveryTag(),true);
            }
        };

        //监听队列
        channel.basicConsume(MqConst.DIRECT_QUEUE1,false,consumer);

    }
}

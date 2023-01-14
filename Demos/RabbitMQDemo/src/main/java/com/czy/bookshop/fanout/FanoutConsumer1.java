package com.czy.bookshop.fanout;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutConsumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(MqConst.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);

        //声明队列
        channel.queueDeclare(MqConst.FANOUT_QUEUE1,false,false,false,null);

        //绑定交换机
        channel.queueBind(MqConst.FANOUT_QUEUE1,MqConst.FANOUT_EXCHANGE,"");


        //定义队列消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println(" [接收到消息]  : " + msg + "!");

                //手动ack
                channel.basicAck(envelope.getDeliveryTag(),true);
            }
        };

        //监听队列
        channel.basicConsume(MqConst.FANOUT_QUEUE1,false,consumer);

    }
}

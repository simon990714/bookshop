package com.czy.bookshop.tx;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConfirmProducer {

    //本测试复用directExchange
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //1.对接channel
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //2.开启确认模式
        channel.confirmSelect();

        //3.添加确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("--ack--");
                System.out.println(deliveryTag);
                System.out.println(multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("--nack--");
                System.out.println(deliveryTag);
                System.out.println(multiple);
            }
        });


        channel.basicPublish(MqConst.DIRECT_EXCHANGE, "error", MessageProperties.PERSISTENT_BASIC, "这是错误消息！".getBytes());
        System.out.println("send msg: first");
        TimeUnit.SECONDS.sleep(10);


        channel.basicPublish(MqConst.DIRECT_EXCHANGE, "info", MessageProperties.PERSISTENT_BASIC, "这是消息！".getBytes());
        System.out.println("send msg: second");
        TimeUnit.SECONDS.sleep(10);






//        //2.发送消息
//        try {
//
//            //2.2发送消息1
//            channel.basicPublish(MqConst.DIRECT_EXCHANGE, "error", MessageProperties.PERSISTENT_BASIC, "这是错误消息！".getBytes());
//
//            //2.3报错
//            int i = 1/0;
//
//            //2.2发送消息2
//            channel.basicPublish(MqConst.DIRECT_EXCHANGE, "info", MessageProperties.PERSISTENT_BASIC, "这是消息！".getBytes());
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("消息发送异常");
//        }


        //3.关闭连接
        channel.close();
        connection.close();
    }
}

package com.czy.bookshop.direct;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(MqConst.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);


        //路由的不同就在于发送消息时要指定routingKey，匹配的队列才能接受到
        channel.basicPublish(MqConst.DIRECT_EXCHANGE,"error",null,"这是一条错误信息".getBytes());
        channel.basicPublish(MqConst.DIRECT_EXCHANGE,"info",null,"这是一条正常信息".getBytes());
        channel.basicPublish(MqConst.DIRECT_EXCHANGE,"warning",null,"这是一条警告信息".getBytes());

        channel.close();
        connection.close();
    }
}

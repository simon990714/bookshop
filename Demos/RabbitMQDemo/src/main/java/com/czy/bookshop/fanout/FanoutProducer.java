package com.czy.bookshop.fanout;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(MqConst.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);

        String msg = "this is fanout 的一条信息";

        channel.basicPublish(MqConst.FANOUT_EXCHANGE,"",null,msg.getBytes());

        channel.close();
        connection.close();
    }
}

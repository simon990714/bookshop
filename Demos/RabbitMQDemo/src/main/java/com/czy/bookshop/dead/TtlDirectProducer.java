package com.czy.bookshop.dead;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Date;

public class TtlDirectProducer {

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        String msg = "这是一条死信msg";
        System.out.println("业务队列发送消息："+ msg);
        System.out.println("消息发送时间："+new Date());
        channel.basicPublish(MqConst.NORMAL_EXCHANGE,"ttl_key",null,msg.getBytes());
    }
}

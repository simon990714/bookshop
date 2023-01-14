package com.czy.bookshop.topic;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicDeclare {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(MqConst.TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(MqConst.TOPIC_QUEUE1,false,false,false,null);
        channel.queueDeclare(MqConst.TOPIC_QUEUE2,false,false,false,null);
        channel.queueBind(MqConst.TOPIC_QUEUE1,MqConst.TOPIC_EXCHANGE,"*.*.txt");
        channel.queueBind(MqConst.TOPIC_QUEUE1,MqConst.TOPIC_EXCHANGE,"*.monday.*");
        channel.queueBind(MqConst.TOPIC_QUEUE2,MqConst.TOPIC_EXCHANGE,"June.#");
        channel.close();
        connection.close();
    }
}

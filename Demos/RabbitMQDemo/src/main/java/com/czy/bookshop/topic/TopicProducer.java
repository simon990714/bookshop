package com.czy.bookshop.topic;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class TopicProducer {
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        send(channel,"这是第一条！","opt.monday.txt");
        send(channel,"这是第二条！","opt.good.txt");
        send(channel,"这是第三条！","June.monday.png");
        send(channel,"这是第四条！","opt.friday.png");
        send(channel,"这是第五条！","June.friday.png");
        send(channel,"这是第六条！","June.friday.png");
    }


    public static void send(Channel channel,String msg,String routingKey) throws IOException {
        channel.basicPublish(MqConst.TOPIC_EXCHANGE,routingKey,null,msg.getBytes());
        System.out.println("生产者发送了一条数据[" + routingKey +"]： " + msg);
    }
}

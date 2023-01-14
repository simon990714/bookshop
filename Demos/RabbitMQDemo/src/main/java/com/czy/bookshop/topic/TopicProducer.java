package com.czy.bookshop.topic;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

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
        //消息持久化，在 props 里面加上  MessageProperties.PERSISTENT_BASIC  ,表示坚持不懈持久化
        channel.basicPublish(MqConst.TOPIC_EXCHANGE,routingKey, MessageProperties.PERSISTENT_BASIC,msg.getBytes());
        System.out.println("生产者发送了一条数据[" + routingKey +"]： " + msg);
    }
}

package com.czy.bookshop.Simple;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleProducer2 {

    public static void main(String[] args) throws IOException, TimeoutException {
        produceMsg("这是  simple  生产者  发送的第二条消息！");
    }

    public static void produceMsg(String msg) throws IOException, TimeoutException {


//        2.创建连接
        Connection connection = ConnectionUtils.getConnection();

//        3.创建通道
        Channel channel = connection.createChannel();

//        4.声明一个队列
        channel.queueDeclare(MqConst.SIMPLE_QUEUE,false,false,false,null);

//        5.发消息
        channel.basicPublish("",MqConst.SIMPLE_QUEUE,null,msg.getBytes());

//        6.关闭资源
        channel.close();
        connection.close();

    }

}

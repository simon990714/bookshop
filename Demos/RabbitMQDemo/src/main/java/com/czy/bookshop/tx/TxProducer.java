package com.czy.bookshop.tx;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxProducer {

    //本测试复用directExchange
    public static void main(String[] args) throws IOException, TimeoutException {

        //1.对接channel
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //2.发送消息
        try {
            //2.1开启事务
            channel.txSelect();

            //2.2发送消息1
            channel.basicPublish(MqConst.DIRECT_EXCHANGE, "error", MessageProperties.PERSISTENT_BASIC, "这是错误消息！".getBytes());

            //2.3报错
            int i = 1/0;

            //2.2发送消息2
            channel.basicPublish(MqConst.DIRECT_EXCHANGE, "info", MessageProperties.PERSISTENT_BASIC, "这是消息！".getBytes());

            //2.2提交事务
            channel.txCommit();

        } catch (Exception e) {
            e.printStackTrace();
            //2.3回滚
            channel.txRollback();

        }


        //3.关闭连接
        channel.close();
        connection.close();
    }
}

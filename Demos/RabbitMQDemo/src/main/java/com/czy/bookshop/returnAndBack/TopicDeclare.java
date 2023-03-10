package com.czy.bookshop.returnAndBack;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class TopicDeclare {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        Map<String,Object> map = new HashMap<>();
        map.put("alternate-exchange",MqConst.BACK_EXCHANGE);
        channel.exchangeDeclare(MqConst.RETURN_EXCHANGE, BuiltinExchangeType.TOPIC,true,false,map);


        channel.queueDeclare(MqConst.RETURN_QUEUE1,true,false,false,null);
        channel.queueDeclare(MqConst.RETURN_QUEUE2,true,false,false,null);

        channel.queueBind(MqConst.RETURN_QUEUE1,MqConst.RETURN_EXCHANGE,"text.*");
        channel.queueBind(MqConst.RETURN_QUEUE2,MqConst.RETURN_EXCHANGE,"picture.*");



        channel.close();
        connection.close();
    }
}

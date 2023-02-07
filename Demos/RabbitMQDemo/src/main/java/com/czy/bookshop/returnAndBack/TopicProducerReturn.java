package com.czy.bookshop.returnAndBack;

import com.czy.bookshop.ConnectionUtils;
import com.czy.bookshop.MqConst;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TopicProducerReturn {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //定义return监听
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("replyCode---->"+replyCode);
                System.out.println("replyText---->"+replyText);
                System.out.println("exchange----->"+exchange);
                System.out.println("routingKey---->"+routingKey);
                System.out.println("body------>"+new String(body));
            }
        });

        //mandatory设置为true，则在消息没有匹配的情况下，回退给生产者
        channel.basicPublish(MqConst.RETURN_EXCHANGE,"text.ss", true , MessageProperties.PERSISTENT_BASIC,"这是一条text消息".getBytes());


        TimeUnit.SECONDS.sleep(2L);


        channel.basicPublish(MqConst.RETURN_EXCHANGE,"ada.aa", true,MessageProperties.PERSISTENT_BASIC,"这是一条接受不到的消息".getBytes());


        TimeUnit.SECONDS.sleep(2L);


        channel.close();
        connection.close();
    }
}

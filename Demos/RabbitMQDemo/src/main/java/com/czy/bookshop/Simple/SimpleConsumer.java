package com.czy.bookshop.Simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleConsumer {
    private static String SIMPLE_QUEUE = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        produceMsg();
    }

    public static void produceMsg() throws IOException, TimeoutException {

//        1.创建工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

//        2.创建连接
        Connection connection = connectionFactory.newConnection();

//        3.创建通道
        Channel channel = connection.createChannel();

//        4.声明一个队列
        channel.queueDeclare(SIMPLE_QUEUE,false,false,false,null);

//        5.声明一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);

                try{
                    System.out.println("对数据进行相关操纵");
//                    int i = 1/0;
                    System.out.println("这是  simple  消费者  接受的第一条消息：" + msg);
                }catch (Exception e){
                    e.printStackTrace();
                    //envelope的deliveryTag相当于msg的索引
                    channel.basicNack(envelope.getDeliveryTag(),false,true);//第一个b代表multiply，与tag有关
                }
//                channel.basicAck(envelope.getDeliveryTag(),true);//tag小于当前的消息全部确认
//                channel.basicAck(envelope.getDeliveryTag(),false);//tag为当前的消息全部确认
            }
        };

//        6.让消费者保持运行
//        channel.basicConsume(SIMPLE_QUEUE,consumer);//没有使用ack
//        channel.basicConsume(SIMPLE_QUEUE,true,consumer);//这是自动ack
        channel.basicConsume(SIMPLE_QUEUE,false,consumer);//这是手动ack

    }

}

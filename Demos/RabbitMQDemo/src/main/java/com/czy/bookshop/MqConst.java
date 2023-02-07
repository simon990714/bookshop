package com.czy.bookshop;

public class MqConst {
    //简单
    public static final String SIMPLE_QUEUE = "simple_queue";

    //fanout
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String FANOUT_QUEUE1 = "fanout_queue1";
    public static final String FANOUT_QUEUE2 = "fanout_queue2";

    //direct
    public static final String DIRECT_EXCHANGE = "direct_exchange";
    public static final String DIRECT_QUEUE1 = "direct_queue1";
    public static final String DIRECT_QUEUE2 = "direct_queue2";


    //topic
    public static final String TOPIC_EXCHANGE = "topic_exchange";
    public static final String TOPIC_QUEUE1 = "topic_queue1";
    public static final String TOPIC_QUEUE2 = "topic_queue2";



    //return
    public static final String RETURN_EXCHANGE = "return_exchange";
    public static final String RETURN_QUEUE1 = "return_queue1";
    public static final String RETURN_QUEUE2 = "return_queue2";


    //back
    public static final String BACK_EXCHANGE = "back_exchange";
    public static final String BACK_QUEUE = "back_queue";

    //dead
    public static String DEAD_EXCHANGE="dead_exchange";
    public static String DEAD_QUEUE="dead_queue";
    public static String DeadLetterRoutingKey="DeadLetterRoutingKey";
    public static String NORMAL_EXCHANGE="normal_exchange";
    public static String NORMAL_QUEUE="normal_queue";
    public static String ROUTING_KEY="ttl_key";





}

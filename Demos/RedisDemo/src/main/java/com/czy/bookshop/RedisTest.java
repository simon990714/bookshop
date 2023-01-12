package com.czy.bookshop;
import com.google.gson.Gson;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisTest {
    @Test
    public void test(){
//        1.连接上redis
        Jedis jedis = new Jedis("192.168.131.128", 6379);


//        2.添加并查询
        jedis.set("k1","v1");
        System.out.println(jedis.get("k1"));

        jedis.del("names");
        jedis.lpush("names","jack","rick","morty");
        List<String> names = jedis.lrange("names", 0, -1);
        System.out.println(names);
        System.out.println(new Gson().toJson(names));

//        3.关闭redis
        jedis.close();
    }
}

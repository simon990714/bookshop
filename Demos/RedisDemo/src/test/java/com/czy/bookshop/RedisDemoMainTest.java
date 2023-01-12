package com.czy.bookshop;

import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


@SpringBootTest
public class RedisDemoMainTest {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate ;

    @Autowired
    StringRedisTemplate stringRedisTemplate ;

    @Autowired
    RedisTemplate<String,Object> stringObjectRedisTemplate;


    @Test
    void testTemplate(){

        String key = "k1";
        String value = "v1";

        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
        System.out.println(valueOperations.get(key));

        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(key,value);
        System.out.println(stringValueOperations.get(key));

    }


    @Test
    void testSerializerTest(){
        Person rick = new Person("rick", 88);
        ValueOperations<String, Object> stringObjectValueOperations = stringObjectRedisTemplate.opsForValue();
        stringObjectValueOperations.set("rick",rick);
        System.out.println(stringObjectValueOperations.get("rick"));

    }

}

package com.czy.qiantai.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class MyCacheSerializer {

    /**
     * 自定义BootCache的redsi序列化
     * @return
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer serializeCustomize() {
        return builder -> builder.cacheDefaults(
                RedisCacheConfiguration.defaultCacheConfig()
                        // 指定值序列器为 json（GenericJackson2JsonRedisSerializer）
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                                RedisSerializer.json()
                        ))
                // 还可以指定默认缓存过期时间
                //.entryTtl(Duration.ofHours(1L))
        );
    }



}


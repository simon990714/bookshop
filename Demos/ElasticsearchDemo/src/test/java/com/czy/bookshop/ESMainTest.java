package com.czy.bookshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest
public class ESMainTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void testES() {
        //创建索引
        elasticsearchRestTemplate.createIndex(Item.class);
        //关联mapping
        elasticsearchRestTemplate.putMapping(Item.class);
        //删除索引库
//        elasticsearchRestTemplate.deleteIndex(Item.class);
    }
}

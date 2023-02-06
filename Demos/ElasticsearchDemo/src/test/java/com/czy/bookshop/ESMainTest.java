package com.czy.bookshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;

@SpringBootTest
public class ESMainTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ItemRepository itemRepository;


    @Test
    void testES() {
        //创建索引
        elasticsearchRestTemplate.createIndex(Item.class);
        //关联mapping
        elasticsearchRestTemplate.putMapping(Item.class);
        //删除索引库
//        elasticsearchRestTemplate.deleteIndex(Item.class);
    }

    @Test
    void createDocument(){
        //增加（修改）单条数据
        itemRepository.save(new Item(1L,"华为Mate","手机","华为",new BigDecimal(5999.0),"http://localhost:8080/001.png"));


        //增加（修改）多条数据
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(1L,"华为Mate","手机","华为",new BigDecimal(5999.0),"http://localhost:8080/001.png"));
        items.add(new Item(2L,"苹果14pm","手机","苹果",new BigDecimal(9999.0),"http://localhost:8080/001.png"));
        items.add(new Item(3L,"小米12","手机","小米",new BigDecimal(3999.0),"http://localhost:8080/001.png"));
        itemRepository.saveAll(items);
    }


}

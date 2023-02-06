package com.czy.bookshop;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

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

    /**
     * 普通查找
     */
    @Test
    void normalSearch(){
        //查询所有
        itemRepository.findAll(Sort.by(Sort.Direction.DESC,"price").and(Sort.by(Sort.Direction.DESC,"brand"))).forEach(System.out::println);
    }


    /**
     * 高级查找
     */
    @Test
    void advancedSearch(){
        //基本查询
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "小米");//会分词来进行匹配
        itemRepository.search(queryBuilder).forEach(System.out::println);

        System.out.println("-----------------------------------");

        //自定义查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(queryBuilder);
        Page<Item> page = itemRepository.search(nativeSearchQueryBuilder.build());
        //打印
        System.out.println(page.getTotalPages());
        System.out.println(page.getTotalElements());
        page.forEach(System.out::println);

        System.out.println("-----------------------------------");

        //分页查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category","手机");//不会进行分词
        NativeSearchQueryBuilder nativeSearchQueryBuilder2 = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder2.withQuery(termQueryBuilder);
        nativeSearchQueryBuilder2.withPageable(PageRequest.of(0,2));//是从第0页开始的
        Page<Item> page2 = itemRepository.search(nativeSearchQueryBuilder2.build());
        //打印
        System.out.println(page2.getTotalPages());
        page2.forEach(System.out::println);


        //排序
        nativeSearchQueryBuilder2.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        Page<Item> page3 = itemRepository.search(nativeSearchQueryBuilder2.build());
        //打印
        System.out.println(page3.getTotalPages());
        page3.forEach(System.out::println);

    }




}

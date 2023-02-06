package com.czy.bookshop;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    void createDocument() {
        //增加（修改）单条数据
        itemRepository.save(new Item(1L, "华为Mate", "手机", "华为", new BigDecimal(5999.0), "http://localhost:8080/001.png"));


        //增加（修改）多条数据
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(1L, "华为Mate", "手机", "华为", new BigDecimal(5999.0), "http://localhost:8080/001.png"));
        items.add(new Item(2L, "苹果14pm", "手机", "苹果", new BigDecimal(9999.0), "http://localhost:8080/001.png"));
        items.add(new Item(3L, "小米12", "手机", "小米", new BigDecimal(3999.0), "http://localhost:8080/001.png"));
        itemRepository.saveAll(items);
    }

    /**
     * 普通查找
     */
    @Test
    void normalSearch() {
        //查询所有
        itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price").and(Sort.by(Sort.Direction.DESC, "brand"))).forEach(System.out::println);
    }


    /**
     * 高级查找
     */
    @Test
    void advancedSearch() {
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
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");//不会进行分词
        NativeSearchQueryBuilder nativeSearchQueryBuilder2 = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder2.withQuery(termQueryBuilder);
        nativeSearchQueryBuilder2.withPageable(PageRequest.of(0, 2));//是从第0页开始的
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


    /**
     * 聚合为桶
     */
    @Test
    public void testAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 结果中不带任何字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        ParsedStringTerms agg = (ParsedStringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<? extends Terms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (Terms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }
    }

    /**
     * 嵌套聚合，求平均值
     */

    @Test
    public void testSubAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        ParsedStringTerms agg = (ParsedStringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<? extends Terms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (Terms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            ParsedAvg avg = (ParsedAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }

    }


}

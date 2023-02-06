package com.czy.qiantai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy.qiantai.entity.Book;
import com.czy.qiantai.es.EsBookRepository;
import com.czy.qiantai.mapper.BookMapper;
import com.czy.qiantai.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy.qiantai.vo.EsBook;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:18
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private EsBookRepository esBookRepository;
    @Override
//    @Cacheable(value = "book",key = "#root.targetClass+#root.methodName")
    public Page<Book> getTopNBook(Integer pageNo, Integer topN){
        Page<Book> bookPage = new Page<>(pageNo,topN);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("buycount");
        Page<Book> bookPageResult = bookMapper.selectPage(bookPage, queryWrapper);
        return bookPageResult;
    }

    @Override
//    @Cacheable(value = "book",key = "#root.targetClass+#root.methodName")
    public Page<Book> getPageBooksByTypeId(Integer currentPage, Integer pageSize, Integer typeId) {
        Page<Book> bookPage = new Page<>(currentPage,pageSize);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("typeId",typeId);
        Page<Book> bookPageResult = bookMapper.selectPage(bookPage, queryWrapper);
        return bookPageResult;
    }

    @Override
    public Page<EsBook> searchBooksByEs(Integer currentPage, Integer pageSize, String searchKey) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //查询条件
        queryBuilder.withQuery(QueryBuilders.multiMatchQuery(searchKey,"name", "typeName", "provider", "author", "detail"));
        //分页条件
        queryBuilder.withPageable(PageRequest.of(currentPage-1,pageSize));
        //价格降序
        queryBuilder.withSort(SortBuilders.fieldSort("buycount").order(SortOrder.DESC));
        //查询
        org.springframework.data.domain.Page<EsBook> esBookPage = esBookRepository.search(queryBuilder.build());
        //把esPage转换成我们前端需要的page
        Page<EsBook> page = new Page<>(currentPage,pageSize);
        page.setRecords(esBookPage.getContent());
        page.setPages(esBookPage.getTotalPages());
        page.setTotal(esBookPage.getTotalElements());
        return page;
    }
}

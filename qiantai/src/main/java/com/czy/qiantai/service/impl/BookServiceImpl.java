package com.czy.qiantai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy.qiantai.entity.Book;
import com.czy.qiantai.mapper.BookMapper;
import com.czy.qiantai.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Override
    @Cacheable(value = "book",key = "#root.targetClass+#root.methodName")
    public Page<Book> getTopNBook(Integer pageNo, Integer topN){
        Page<Book> bookPage = new Page<>(pageNo,topN);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("buycount");
        Page<Book> bookPageResult = bookMapper.selectPage(bookPage, queryWrapper);
        return bookPageResult;
    }

    @Override
    @Cacheable(value = "book",key = "#root.targetClass+#root.methodName")
    public Page<Book> getPageBooksByTypeId(Integer currentPage, Integer pageSize, Integer typeId) {
        Page<Book> bookPage = new Page<>(currentPage,pageSize);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("buycount");
        queryWrapper.eq("typeId",typeId);
        Page<Book> bookPageResult = bookMapper.selectPage(bookPage, queryWrapper);
        return bookPageResult;
    }
}

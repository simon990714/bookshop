package com.czy.qiantai.service.impl;

import com.czy.qiantai.entity.Book;
import com.czy.qiantai.mapper.BookMapper;
import com.czy.qiantai.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}

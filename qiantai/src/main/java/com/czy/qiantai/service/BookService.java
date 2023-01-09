package com.czy.qiantai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy.qiantai.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:18
 */
public interface BookService extends IService<Book> {

    Page<Book> getTopNBook(Integer pageNo, Integer topN);
}

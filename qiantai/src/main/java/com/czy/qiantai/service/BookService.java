package com.czy.qiantai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy.qiantai.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy.qiantai.vo.EsBook;

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

    Page<Book> getPageBooksByTypeId(Integer currentPage, Integer pageSize, Integer typeId);

    Page<EsBook> searchBooksByEs(Integer currentPage, Integer pageSize, String searchKey);
}

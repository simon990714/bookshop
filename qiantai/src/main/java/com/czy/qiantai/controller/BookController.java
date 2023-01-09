package com.czy.qiantai.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy.qiantai.entity.Book;
import com.czy.qiantai.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:18
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("topNine")
    @ResponseBody
    public List<Book> topNine(Integer topN){
        Page<Book> topNBook = bookService.getTopNBook(1,topN);
        return topNBook.getRecords();
    }

}


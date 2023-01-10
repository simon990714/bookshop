package com.czy.qiantai.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy.qiantai.entity.Book;
import com.czy.qiantai.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
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
@Controller
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

    @RequestMapping("singleBook")
    public String singleBook(Integer bookId, Model model){
        Book book = bookService.getById(bookId);
        System.out.println(book);
        model.addAttribute("book",book);
        return "singleBook";
    }

    @RequestMapping("booksOfType")
    public String booksOfType(Integer typeId,Model model){
        model.addAttribute("typeId",typeId);
        return "booksOfType";
    }

    @RequestMapping("pageBooksOfType")
    @ResponseBody
    public Page<Book> pageBooksOfType(
            @RequestParam(defaultValue = "1") Integer currentPage ,
            @RequestParam(defaultValue = "9") Integer pageSize,
            Integer typeId
    ){
        return bookService.getPageBooksByTypeId(currentPage,pageSize,typeId);
    }

}


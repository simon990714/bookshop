package com.czy.qiantai.controller;


import com.czy.qiantai.entity.Booktype;
import com.czy.qiantai.service.BooktypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
@RestController
@RequestMapping("/booktype")
public class BooktypeController {

    @Autowired
    private BooktypeService booktypeService;

    @RequestMapping("all")
    public List<Booktype> all(){
        List<Booktype> list = booktypeService.list();
        return list;
    }

}


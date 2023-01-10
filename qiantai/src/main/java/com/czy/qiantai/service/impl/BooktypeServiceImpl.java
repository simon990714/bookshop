package com.czy.qiantai.service.impl;

import com.czy.qiantai.entity.Booktype;
import com.czy.qiantai.mapper.BooktypeMapper;
import com.czy.qiantai.service.BooktypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
@Service
public class BooktypeServiceImpl extends ServiceImpl<BooktypeMapper, Booktype> implements BooktypeService {

}

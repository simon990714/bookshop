package com.czy.qiantai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy.qiantai.entity.Book;
import com.czy.qiantai.mapper.BookMapper;
import com.czy.qiantai.service.MyCartService;
import com.czy.qiantai.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;

@Service
public class MyCartServiceImpl implements MyCartService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private BookMapper bookMapper;


    @Override
    public void addCart(Long userId, Long bookId) {
        //获取图书信息
        Book book = bookMapper.selectById(bookId);
        //判断是否存储过
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Object o = hashOperations.get(userId + "", bookId+"");
        CartItem cartItem = null;
        if (o == null){
            //没存过，直接放入
            cartItem = new CartItem();
            cartItem.setBookId(bookId);
            cartItem.setBookName(book.getName());
            cartItem.setImgSrc(book.getImgsrc());
            cartItem.setItemNum(1);
            cartItem.setBookPrice(book.getPrice());
            cartItem.setSumPrice(book.getPrice().multiply(new BigDecimal(1)));
        }else {
            //存过了，更改数据
            cartItem = (CartItem) o;
            cartItem.setItemNum(cartItem.getItemNum()+1);
            cartItem.setSumPrice(cartItem.getSumPrice().add(cartItem.getBookPrice()));
        }
        //储存到redis
        hashOperations.put(userId+"",bookId+"",cartItem);
        }

    @Override
    public Collection<Object> getAllItems(Long userId) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(""+userId);
    }
}

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
import java.util.List;

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

    @Override
    public void updateItemNum(Long userId, Long bookId, Integer itemNum) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        CartItem cartItem = (CartItem) hashOperations.get("" + userId, "" + bookId);
        Integer currentItemNum = cartItem.getItemNum();

        //1.itemNum = +1
        if (itemNum == 1){
            cartItem.setItemNum(cartItem.getItemNum()+1);
            cartItem.setSumPrice(cartItem.getSumPrice().add(cartItem.getBookPrice()));
            hashOperations.put(userId+"",bookId+"",cartItem);
            return;
        }
        //2.itemNum = -1
        if (itemNum == -1){
            //item不超过1，则为删除
            if (currentItemNum <= 1){
                hashOperations.delete(userId+"",bookId+"");
            }else {
                cartItem.setItemNum(cartItem.getItemNum()-1);
                cartItem.setSumPrice(cartItem.getSumPrice().subtract(cartItem.getBookPrice()));
                hashOperations.put(userId+"",bookId+"",cartItem);
            }
            return;
        }
        //3.itemNum = 0，删除
        if (itemNum == 0){
            hashOperations.delete(userId+"",bookId+"");
        }

    }

    @Override
    public BigDecimal calTotalPrice(Long userId, Long[] bookIds) {
        List<Object> values = redisTemplate.opsForHash().values(userId + "");

        BigDecimal bigDecimal = new BigDecimal("0.00");
        //双层循环，如果values中的id和选中的id一样，进行累加
        for (Object value : values) {
            CartItem cartItem = (CartItem) value;
            for (Long bookId : bookIds) {
                if (cartItem.getBookId().equals(bookId)){
                    bigDecimal = bigDecimal.add(cartItem.getSumPrice());
                    break;
                }
            }
        }
        return bigDecimal;
    }
}

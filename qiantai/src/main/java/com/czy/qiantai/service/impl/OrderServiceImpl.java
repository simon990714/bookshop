package com.czy.qiantai.service.impl;

import com.czy.qiantai.entity.Order;
import com.czy.qiantai.mapper.OrderMapper;
import com.czy.qiantai.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy.qiantai.vo.CartItem;
import com.czy.qiantai.vo.CartOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public CartOrder getCartOrder(Long userId, Long[] bookIds) {
        //计算总价和获取cartItem数组
        BigDecimal bigDecimal = new BigDecimal("0.00");
        List<CartItem> returnItems = new ArrayList<>();
        CartOrder cartOrder = new CartOrder();
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        List<Object> allItems = hashOperations.values("" + userId);

        for (Object item : allItems) {
            CartItem cartItem = (CartItem) item;
            for (Long bookId : bookIds) {
                if (cartItem.getBookId().equals(bookId)){
                    bigDecimal = bigDecimal.add(cartItem.getSumPrice());
                    returnItems.add(cartItem);
                }
            }
        }
        cartOrder.setCartItems(returnItems);
        cartOrder.setTotalPrice(bigDecimal);
        return cartOrder;
    }
}

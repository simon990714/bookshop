package com.czy.qiantai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy.qiantai.entity.Book;
import com.czy.qiantai.entity.Item;
import com.czy.qiantai.entity.Order;
import com.czy.qiantai.mapper.BookMapper;
import com.czy.qiantai.mapper.ItemMapper;
import com.czy.qiantai.mapper.OrderMapper;
import com.czy.qiantai.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy.qiantai.vo.CartItem;
import com.czy.qiantai.vo.CartOrder;
import com.czy.qiantai.vo.OrderItem;
import com.czy.qiantai.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    private OrderMapper orderMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private BookMapper bookMapper;

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

    @Override
    public int createOrder(Long userId, Long[] bookIds, Long addressId) {
        CartOrder cartOrder = getCartOrder(userId, bookIds);
        List<CartItem> cartItems = cartOrder.getCartItems();
        BigDecimal totalPrice = cartOrder.getTotalPrice();
        Order order = new Order();
        String orderNum = "woniu" + new Date().getTime();
        order.setOrderNum(orderNum);
        order.setUserId(userId);
        order.setAddressId(addressId);
        order.setCreatetime(new Date());
        order.setState(1);  //订单状态 1.未支付  2 . 已支付  3.退款中  4. 已退款  5.已取消
        return orderMapper.insert(order);
    }

    @Override
    public List<OrderVo> getOrderVo(Long userId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("userId",userId);
        List<Order> orders = orderMapper.selectList(orderQueryWrapper);
        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orders) {
            OrderVo orderVo = new OrderVo();
            Long orderId = order.getId();
            QueryWrapper<Item> itemQueryWrapper = new QueryWrapper<>();
            itemQueryWrapper.eq("orderId",orderId);
            List<Item> items = itemMapper.selectList(itemQueryWrapper);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            for (Item item : items) {
                Book book = bookMapper.selectById(item.getBookId());
                OrderItem orderItem = new OrderItem();
                orderItem.setBookName(item.getBookName());
                orderItem.setItemNum(item.getBcount());
                orderItem.setImgSrc(book.getImgsrc());
                orderItem.setSumPrice(item.getPrice());
                orderItems.add(orderItem);
            }
            orderVo.setId(orderId);
            orderVo.setTotalPrice(order.getTotalprice());
            orderVo.setOrderTime(order.getCreatetime());
            orderVo.setOrderNum(order.getOrderNum());
            orderVo.setOrderItemList(orderItems);
            orderVo.setState(order.getState());
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }
}

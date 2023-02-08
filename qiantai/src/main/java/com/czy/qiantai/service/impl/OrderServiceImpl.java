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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private RabbitTemplate rabbitTemplate;

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
    public String createOrder(Long userId, Long[] bookIds, Long addressId) {
        CartOrder cartOrder = getCartOrder(userId, bookIds);
        //生成订单
        List<CartItem> cartItems = cartOrder.getCartItems();
        BigDecimal totalPrice = cartOrder.getTotalPrice();
        Order order = new Order();
        String orderNum = "WONIU" + new Date().getTime();
        order.setOrderNum(orderNum);
        order.setUserId(userId);
        order.setAddressId(addressId);
        Date now = new Date();
        order.setCreatetime(now);
        order.setState(1);  //订单状态 1.未支付  2 . 已支付  3.退款中  4. 已退款  5.已取消
        order.setTotalprice(totalPrice);
        orderMapper.insert(order);
        System.out.println("订单创建时间：" + new Date());

        //消息发送给延迟队列，15分钟后判断是否支付
        rabbitTemplate.convertAndSend("delay_exchange","confirm.order",order.getId());


        //生成订单项目
        for (CartItem cartItem : cartItems) {
            Book book = bookMapper.selectById(cartItem.getBookId());
            Item item = new Item();
            item.setOrderId(order.getId());
            item.setState(1);
            item.setCreatetime(now);
            item.setBcount(cartItem.getItemNum());
            item.setPrice(book.getPrice());
            item.setBookName(book.getName());
            item.setBookId(book.getId());
            item.setSumprice(cartItem.getSumPrice());
            itemMapper.insert(item);
        }
        //删除购物车
        HashOperations opsForHash = redisTemplate.opsForHash();
        String[] bookIdsString = new String[bookIds.length];
        for (int i = 0; i < bookIds.length; i++) {
            bookIdsString[i] = bookIds[i]+"";
        }
        opsForHash.delete(userId+"",bookIdsString);
        return "ok";
    }

    @Override
    public List<OrderVo> getOrderVo(Long userId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("userId",userId);
        orderQueryWrapper.orderByDesc("createtime");
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

    @Override
    public void updateOrderState(Long orderId, Integer newState) {
        Order order = orderMapper.selectById(orderId);
        order.setState(newState);
        orderMapper.updateById(order);
    }
}

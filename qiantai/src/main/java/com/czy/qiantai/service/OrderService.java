package com.czy.qiantai.service;

import com.czy.qiantai.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy.qiantai.vo.CartOrder;
import com.czy.qiantai.vo.OrderVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
public interface OrderService extends IService<Order> {

    CartOrder getCartOrder(Long id, Long[] bookIds);

    String createOrder(Long userId, Long[] bookIds, Long addressId);

    List<OrderVo> getOrderVo(Long userId);

    void updateOrderState(Long orderId, Integer newState);
}

package com.czy.qiantai.service.impl;

import com.czy.qiantai.entity.Order;
import com.czy.qiantai.mapper.OrderMapper;
import com.czy.qiantai.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}

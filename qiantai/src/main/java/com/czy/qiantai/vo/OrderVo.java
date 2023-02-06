package com.czy.qiantai.vo;

import com.czy.qiantai.entity.Address;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class OrderVo {
    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 总金额
     */
    private BigDecimal totalPrice;

    /**
     * 订单状态
     */
    private Integer state;

    /**
     * 所有订单项
     */
    private List<OrderItem> orderItemList;
}

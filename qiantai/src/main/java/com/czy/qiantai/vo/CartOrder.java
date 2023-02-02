package com.czy.qiantai.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartOrder {
    private BigDecimal totalPrice;
    private List<CartItem> cartItems;
}

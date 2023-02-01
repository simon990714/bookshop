package com.czy.qiantai.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem {
    /**
     * 图书id
     */
    private Long bookId;
    /**
     * 图书名称
     */
    private String bookName;
    /**
     * 图书图片
     */
    private String imgSrc;
    /**
     * 图书单价
     */
    private BigDecimal bookPrice;
    /**
     * 购买数量
     */
    private Integer itemNum;
    /**
     * 小计
     */
    private BigDecimal sumPrice;
}

package com.czy.qiantai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
@Getter
@Setter
@TableName("t_item")
public class Item {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("bookId")
    private Long bookId;

    @TableField("bookName")
    private String bookName;

    @TableField("price")
    private BigDecimal price;

    @TableField("bcount")
    private Integer bcount;

    @TableField("sumprice")
    private BigDecimal sumprice;

    @TableField("orderId")
    private Long orderId;

    @TableField("createtime")
    private Date createtime;

    @TableField("state")
    private Integer state;


}

package com.czy.qiantai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_conment")
public class Conment {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("book_id")
    private Integer bookId;

    @TableField("content")
    private String content;

    @TableField("createtime")
    private Date createtime;

    @TableField("status")
    private String status;


}

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
@TableName("t_booktype")
public class Booktype {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("createtime")
    private Date createtime;

    @TableField("updatetime")
    private Date updatetime;

    @TableField("state")
    private Integer state;


}

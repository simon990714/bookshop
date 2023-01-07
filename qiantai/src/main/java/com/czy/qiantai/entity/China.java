package com.czy.qiantai.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_china")
public class China {

    @TableId("Id")
    private Integer id;

    @TableField("Name")
    private String name;

    @TableField("Pid")
    private Integer pid;


}

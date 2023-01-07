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
@TableName("t_user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("nickname")
    private String nickname;

    @TableField("realname")
    private String realname;

    @TableField("mobile")
    private String mobile;

    @TableField("email")
    private String email;

    @TableField("createtime")
    private Date createtime;

    @TableField("updatetime")
    private Date updatetime;

    @TableField("state")
    private Integer state;


}

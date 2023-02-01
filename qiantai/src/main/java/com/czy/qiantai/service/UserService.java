package com.czy.qiantai.service;

import com.czy.qiantai.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
public interface UserService extends IService<User> {

    User getUserByAccount(String username);

    User getUserByEmail(String email);

    int reg(String account, String password, String email);
}

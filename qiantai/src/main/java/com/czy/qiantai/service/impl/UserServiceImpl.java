package com.czy.qiantai.service.impl;

import com.czy.qiantai.entity.User;
import com.czy.qiantai.mapper.UserMapper;
import com.czy.qiantai.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

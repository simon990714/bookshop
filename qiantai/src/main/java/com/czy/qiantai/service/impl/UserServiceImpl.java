package com.czy.qiantai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy.qiantai.entity.User;
import com.czy.qiantai.mapper.UserMapper;
import com.czy.qiantai.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByAccount(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public int reg(String account, String password, String email) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setEmail(email);
        user.setCreatetime(new Date());
        user.setUpdatetime(new Date());
        user.setState(1);
        return userMapper.insert(user);
    }

}

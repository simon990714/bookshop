package com.czy.qiantai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.czy.qiantai.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
public interface MyCartService {

    void addCart(Long userId , Long bookId);
}

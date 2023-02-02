package com.czy.qiantai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy.qiantai.entity.Address;
import com.czy.qiantai.mapper.AddressMapper;
import com.czy.qiantai.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:18
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Override
    public List<Address> getAddressByAccount(Long userId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        return addressMapper.selectList(queryWrapper);
    }
}

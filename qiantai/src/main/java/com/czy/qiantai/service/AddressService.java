package com.czy.qiantai.service;

import com.czy.qiantai.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:18
 */
public interface AddressService extends IService<Address> {

    List<Address> getAddressByAccount(Long userId);

    int saveAddress(Address address);
}

package com.czy.qiantai.mapper;

import com.czy.qiantai.entity.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:18
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {

    @Update("update t_address set isDefault = '0' where userId = #{userId}")
    void clearDefaultAddress(Long userId);
}

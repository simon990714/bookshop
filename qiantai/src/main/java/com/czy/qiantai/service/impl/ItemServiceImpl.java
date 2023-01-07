package com.czy.qiantai.service.impl;

import com.czy.qiantai.entity.Item;
import com.czy.qiantai.mapper.ItemMapper;
import com.czy.qiantai.service.ItemService;
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
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}

package com.czy.qiantai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy.qiantai.entity.China;
import com.czy.qiantai.mapper.ChinaMapper;
import com.czy.qiantai.service.ChinaService;
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
 * @since 2023-01-07 12:01:19
 */
@Service
public class ChinaServiceImpl extends ServiceImpl<ChinaMapper, China> implements ChinaService {
    @Autowired
    private ChinaMapper chinaMapper;

    @Override
    public List<China> queryList(Long pId) {
        QueryWrapper<China> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Pid",pId);
        return chinaMapper.selectList(queryWrapper);
    }
}

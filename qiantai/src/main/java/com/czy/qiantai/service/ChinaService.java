package com.czy.qiantai.service;

import com.czy.qiantai.entity.China;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
public interface ChinaService extends IService<China> {

    List<China> queryList(Long pId);
}

package com.czy.qiantai.controller;


import com.czy.qiantai.entity.China;
import com.czy.qiantai.service.ChinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
@RestController
@RequestMapping("/china")
public class ChinaController {

    @Autowired
    private ChinaService chinaService;

    @GetMapping("queryList")
    public List<China> queryList(Long pId){
        return chinaService.queryList(pId);
    }



}


package com.guigu.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.erp.pojo.Gather;
import com.guigu.erp.service.GathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Gath")
public class GathController {
    @Autowired
    GathService gathService;

    @RequestMapping("/pages")
    public IPage<Gather> pages(@RequestParam(defaultValue = "1") int pageNo,
                               @RequestParam(defaultValue = "5") int pageSize){
        QueryWrapper<Gather> queryWrapper = new QueryWrapper<Gather>();
        queryWrapper.eq("check_tag",0);

       return gathService.page(new Page<Gather>(pageNo,pageSize),queryWrapper);
    }

    @RequestMapping("/Shenheok")
    public int Shenheok(Gather gather){

        return  gathService.updates(gather);
    }
}

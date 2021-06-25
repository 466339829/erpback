package com.guigu.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
    @RequestMapping("/pages2")
    public IPage<Gather> pages2(@RequestParam(defaultValue = "1") int pageNo,
                               @RequestParam(defaultValue = "5") int pageSize){

        return gathService.page(new Page<Gather>(pageNo,pageSize));
    }
    //入库调度显示等待审核
    @RequestMapping("/pages3")
    public IPage<Gather> pages3(@RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "5") int pageSize){
        QueryWrapper<Gather> queryWrapper=new QueryWrapper<Gather>();
        queryWrapper.eq("check_tag",0);
        queryWrapper.eq("gather_tag",1);
        return gathService.page(new Page<Gather>(pageNo,pageSize),queryWrapper);
    }
    //入库审核不通过
    @RequestMapping("/update2")
    public boolean update2(Gather gather){
        gather.setCheckTag("2");
        UpdateWrapper<Gather> updateWrapper=new UpdateWrapper<Gather>();
        updateWrapper.eq("id",gather.getId());
        return gathService.update(gather,updateWrapper);
    }
    //通过
    @RequestMapping("/update")
    public boolean update(Gather gather){
        gather.setCheckTag("1");
        gather.setGatherTag("2");
        UpdateWrapper<Gather> updateWrapper=new UpdateWrapper<Gather>();
        updateWrapper.eq("id",gather.getId());
        return gathService.update(gather,updateWrapper);
    }
}

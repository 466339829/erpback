package com.guigu.erp.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.guigu.erp.pojo.GatherDetails;
import com.guigu.erp.service.GatherDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gath")
public class GatherDetailsController {

    @Autowired
    GatherDetailsService gatherDetailsService;

    @RequestMapping("/gathset")
    public Boolean gathset(@RequestBody List<GatherDetails> gatherDetails){
       return gatherDetailsService.saveBatchExtend(gatherDetails);
    }

    @RequestMapping("/selectByPId/{gid}")
    public List<GatherDetails> selectByPId(@PathVariable Integer gid){
        return gatherDetailsService.selectByPId(gid);
    }

    //表格调度
    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    public boolean update(GatherDetails gatherDetails){
        UpdateWrapper<GatherDetails> updateWrapper=new UpdateWrapper<GatherDetails>();
        gatherDetails.setGatherTag("2");
        updateWrapper.eq("id",gatherDetails.getId());
        return gatherDetailsService.update(gatherDetails,updateWrapper);
    }

}

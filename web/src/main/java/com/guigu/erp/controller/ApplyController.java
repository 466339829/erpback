package com.guigu.erp.controller;

import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Apply;
import com.guigu.erp.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @RequestMapping(value = "/addApply",produces = "application/json;charset=utf-8")
    public boolean saveBatchExtend(@RequestBody List<Apply> applyList){
        return applyService.saveBatchExtend(applyList);
    }

    @RequestMapping("/applyList")
    public PageInfo<Apply> applyList(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                Apply apply) {
        return applyService.applyList(pageNo,pageSize,apply);
    }

    @RequestMapping("/selectByAid")
    public List<Apply> selectByAid(String applyId) {
        return applyService.selectByAid(applyId);
    }

    @RequestMapping("/checkTag")
    public boolean checkTag(Apply apply) {
        return applyService.checkTag(apply);
    }

    @RequestMapping("/page")
    public PageInfo<Apply> queryPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     Apply apply) {
        return applyService.queryPage(pageNo,pageSize,apply);
    }
}

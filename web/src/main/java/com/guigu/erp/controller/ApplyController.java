package com.guigu.erp.controller;

import com.guigu.erp.pojo.Apply;
import com.guigu.erp.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

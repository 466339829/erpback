package com.guigu.erp.controller;

import com.guigu.erp.pojo.DesignProcedureDetails;
import com.guigu.erp.service.DesignProcedureDetailsService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/designProcedureDetails")
public class DesignProcedureDetailsController {
    @Autowired
    private DesignProcedureDetailsService designProcedureDetailsService;

    @RequestMapping("/selectByPid")
    public List<DesignProcedureDetails> selectByPid(String productId) {
        return designProcedureDetailsService.selectByPid(productId);
    }

    //添加
    @RequestMapping(value = "/insert",produces = "application/json;charset=utf-8")
    public ResultUtil insert(@RequestBody List<DesignProcedureDetails> designProcedureDetails){
        return designProcedureDetailsService.saveBatchExtend(designProcedureDetails);
    }

    //删除工序
    @RequestMapping("/delete")
    public ResultUtil delete(int id,int parentId){
        return  designProcedureDetailsService.delete(id,parentId);
    }

    //修改
    @RequestMapping(value = "/update",produces = "application/json;charset=utf-8")
    public ResultUtil update(@RequestBody List<DesignProcedureDetails> designProcedureDetails){
        return designProcedureDetailsService.saveOrUpdateBatchExtend(designProcedureDetails);
    }
}

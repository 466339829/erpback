package com.guigu.erp.controller;

import com.guigu.erp.pojo.ProcedureModule;
import com.guigu.erp.service.ProcedureModuleService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/procedureModule")
public class ProcedureModuleController {
    @Autowired
    private ProcedureModuleService procedureModuleService;

    @RequestMapping("/selectByPid/{parentId}")
    public List<ProcedureModule> selectByPid(@PathVariable int parentId){
        return procedureModuleService.selectByPid(parentId);
    }

    @RequestMapping(value = "/procedureFinish",produces = "application/json;charset=utf-8")
    public ResultUtil setRoleMenu(@RequestBody List<ProcedureModule> procedureModuleList){
        return procedureModuleService.saveOrUpdateBatchExtend(procedureModuleList);
    }
}

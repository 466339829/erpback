package com.guigu.erp.controller;

import com.guigu.erp.pojo.ProcedureModule;
import com.guigu.erp.service.ProcedureModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
}

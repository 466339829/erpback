package com.guigu.erp.controller;

import com.guigu.erp.pojo.DesignProcedureModule;
import com.guigu.erp.service.DesignProcedureModuleService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/designProcedureModule")
public class DesignProcedureModuleController {
    @Autowired
    private DesignProcedureModuleService designProcedureModuleService;

    @RequestMapping(value = "/addDesignProcedureModule",produces = "application/json;charset=utf-8")
    public ResultUtil setRoleMenu(@RequestBody List<DesignProcedureModule> designProcedureModules){
        return designProcedureModuleService.saveBatchExtend(designProcedureModules);
    }

    /**
     * 跟据id查询
     * @param id
     * @return
     */
    @RequestMapping("/selectByPId/{id}")
    public List<DesignProcedureModule> selectByPId(@PathVariable int id){
        return  designProcedureModuleService.selectByPId(id);
    }
}

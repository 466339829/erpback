package com.guigu.erp.controller;

import com.guigu.erp.pojo.Module;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.service.ModuleService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/addModuleDetails",produces = "application/json;charset=utf-8")
    public ResultUtil setRoleMenu(@RequestBody List<ModuleDetails> roleMenus, Module module){
        return moduleService.saveBatchExtend(roleMenus,module);
    }
}

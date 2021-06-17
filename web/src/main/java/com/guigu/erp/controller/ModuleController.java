package com.guigu.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Module;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.service.ModuleService;
import com.guigu.erp.util.ListUtil;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    //添加物料组成
    @RequestMapping(value = "/addModuleDetails",produces = "application/json;charset=utf-8")
    public ResultUtil addModuleDetails(@RequestBody List<ModuleDetails> moduleDetails){
        return moduleService.saveBatchExtend(moduleDetails);
    }

    //条件分页查询
    @RequestMapping("/page")
    public PageInfo<Module> page(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                         Module module){

        return  moduleService.queryPage(pageNo,pageSize,module);
    }

    /**
     * 跟据id查询
     * @param id
     * @return
     */
    @RequestMapping("/selectById/{id}")
    public ListUtil selectById(@PathVariable int id){
        return  moduleService.selectById(id);
    }

    /**
     * 不通过
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ResultUtil delete(@PathVariable int id){
        return  moduleService.deleteById(id);
    }
    /**
     * 不通过
     * @param id
     * @return
     */
    @RequestMapping("/checkTag")
    public ResultUtil checkTag(int id, String checker){
        return  moduleService.checkTag(id,checker);
    }
    //变更物料组成
    @RequestMapping(value = "/updateModuleDetails",produces = "application/json;charset=utf-8")
    public ResultUtil updateModuleDetails(@RequestBody List<ModuleDetails> moduleDetails){
        return moduleService.updateBatchExtend(moduleDetails);
    }
}

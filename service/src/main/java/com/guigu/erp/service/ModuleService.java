package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.Module;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.util.ListUtil;
import com.guigu.erp.util.ResultUtil;

import java.util.List;

public interface ModuleService extends IService<Module> {
    ResultUtil saveBatchExtend(List<ModuleDetails> roleMenus);

    ListUtil selectById(int id);

    ResultUtil deleteById(int id);

    ResultUtil checkTag(int id, String checker);

    ResultUtil updateBatchExtend(List<ModuleDetails> moduleDetails);
}

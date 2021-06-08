package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.File;
import com.guigu.erp.pojo.Module;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.util.ResultUtil;

import java.util.List;

public interface ModuleService extends IService<Module> {
    ResultUtil saveBatchExtend(List<ModuleDetails> roleMenus, Module module);
}

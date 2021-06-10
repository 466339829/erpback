package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.util.ResultUtil;

public interface ModuleDetailsService extends IService<ModuleDetails> {
    ResultUtil deleteById(int id, int parentId);
}

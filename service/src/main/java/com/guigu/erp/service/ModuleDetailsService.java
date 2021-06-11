package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.util.ResultUtil;

import java.util.List;

public interface ModuleDetailsService extends IService<ModuleDetails> {
    ResultUtil deleteById(int id, int parentId);
    List<ModuleDetails> selectByProductId(String productId);
}

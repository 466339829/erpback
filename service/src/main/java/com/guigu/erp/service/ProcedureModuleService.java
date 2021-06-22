package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.ProcedureModule;

import java.util.List;

public interface ProcedureModuleService extends IService<ProcedureModule> {
    List<ProcedureModule> selectByPid(int parentId);
}

package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.DesignProcedureModule;
import com.guigu.erp.util.ResultUtil;

import java.util.List;

public interface DesignProcedureModuleService extends IService<DesignProcedureModule> {
    ResultUtil saveBatchExtend(List<DesignProcedureModule> designProcedureModules);

    List<DesignProcedureModule> selectByPId(int id);

    ResultUtil updateBatchExtend(List<DesignProcedureModule> designProcedureModules);

    ResultUtil updateBatchExtendById(List<DesignProcedureModule> designProcedureModules);
}

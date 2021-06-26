package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.ProcedureModule;
import com.guigu.erp.util.ProcedureModuleUtil;
import com.guigu.erp.util.ResultUtil;

import java.util.List;

public interface ProcedureModuleService extends IService<ProcedureModule> {
    List<ProcedureModule> selectByPid(int parentId);

    ResultUtil saveOrUpdateBatchExtend(List<ProcedureModule> procedureModuleList);

    ProcedureModuleUtil selectShuLiang(int parentId);

    ResultUtil checkProcedureFinish(List<ProcedureModule> procedureModuleList);
}

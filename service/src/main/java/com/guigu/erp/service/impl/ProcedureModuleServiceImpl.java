package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ProcedureModuleMapper;
import com.guigu.erp.pojo.ProcedureModule;
import com.guigu.erp.service.ProcedureModuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureModuleServiceImpl extends ServiceImpl<ProcedureModuleMapper, ProcedureModule> implements ProcedureModuleService {
    @Override
    public List<ProcedureModule> selectByPid(int parentId) {
        QueryWrapper<ProcedureModule> procedureModuleQueryWrapper = new QueryWrapper<>();
        procedureModuleQueryWrapper.eq("parent_id",parentId);
        return this.list(procedureModuleQueryWrapper);
    }
}

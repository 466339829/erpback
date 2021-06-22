package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ProcedureMapper;
import com.guigu.erp.pojo.Procedure;
import com.guigu.erp.service.ProcedureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureServiceImpl extends ServiceImpl<ProcedureMapper, Procedure> implements ProcedureService {
    @Override
    public List<Procedure> selectByPid(int parentId) {
        QueryWrapper<Procedure> procedureQueryWrapper = new QueryWrapper<>();
        procedureQueryWrapper.eq("parent_id",parentId);
        return this.list(procedureQueryWrapper);
    }
}

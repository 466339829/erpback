package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ProcedureMapper;
import com.guigu.erp.pojo.Procedure;
import com.guigu.erp.service.ProcedureService;
import org.springframework.stereotype.Service;

@Service
public class ProcedureServiceImpl extends ServiceImpl<ProcedureMapper, Procedure> implements ProcedureService {
}

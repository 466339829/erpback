package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.Procedure;

import java.util.List;

public interface ProcedureService extends IService<Procedure> {
    List<Procedure> selectByPid(int parentId);
}

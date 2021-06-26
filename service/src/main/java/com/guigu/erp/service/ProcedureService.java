package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.Procedure;
import com.guigu.erp.util.ResultUtil;

import java.util.List;

public interface ProcedureService extends IService<Procedure> {
    List<Procedure> selectByPid(int parentId);

    ResultUtil finishTag(int id, String procedureFinishTag);

    boolean transferTag(int id, Float realAmount);

    boolean checkTransferTag(int id);
}

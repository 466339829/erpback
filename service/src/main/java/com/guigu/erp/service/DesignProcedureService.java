package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.DesignProcedure;

public interface DesignProcedureService extends IService<DesignProcedure> {
    PageInfo<DesignProcedure> queryPage(int pageNo, int pageSize, DesignProcedure designProcedure);
}

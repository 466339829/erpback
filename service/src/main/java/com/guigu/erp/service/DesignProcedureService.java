package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.DesignProcedure;
import com.guigu.erp.util.DesignProcedureUtil;

public interface DesignProcedureService extends IService<DesignProcedure> {
    PageInfo<DesignProcedure> queryPage(int pageNo, int pageSize, DesignProcedure designProcedure);

    DesignProcedureUtil designProcedureDetailsByPId(int id);

    boolean designModuleTag(DesignProcedure designProcedure);


    boolean delete(DesignProcedure designProcedure);
}

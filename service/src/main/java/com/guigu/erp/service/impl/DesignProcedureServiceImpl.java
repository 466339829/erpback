package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.mapper.DesignProcedureMapper;
import com.guigu.erp.pojo.DesignProcedure;
import com.guigu.erp.service.DesignProcedureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignProcedureServiceImpl extends ServiceImpl<DesignProcedureMapper, DesignProcedure> implements DesignProcedureService {

    @Override
    public PageInfo<DesignProcedure> queryPage(int pageNo, int pageSize, DesignProcedure designProcedure) {
        List<DesignProcedure> designProcedureList = null;
        QueryWrapper<DesignProcedure> queryWrapper = new QueryWrapper<>();
        if (designProcedure != null) {
            if (designProcedure.getProductName() != null && designProcedure.getProductName() != "")
                queryWrapper.like("product_name", designProcedure.getProductName());

            if (designProcedure.getCheckTag() != null && designProcedure.getCheckTag() != "")
                queryWrapper.eq("check_tag", designProcedure.getCheckTag());

            if (designProcedure.getDesignModuleChangeTag() != null && designProcedure.getDesignModuleChangeTag() != "")
                queryWrapper.eq("design_module_tag", designProcedure.getDesignModuleChangeTag());

            PageHelper.startPage(pageNo, pageSize);
            designProcedureList = this.list(queryWrapper);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            designProcedureList = this.list(queryWrapper);
        }
        return new PageInfo<DesignProcedure>(designProcedureList);
    }
}

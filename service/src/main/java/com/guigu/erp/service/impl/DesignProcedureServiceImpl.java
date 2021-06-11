package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.mapper.DesignProcedureMapper;
import com.guigu.erp.pojo.DesignProcedure;
import com.guigu.erp.pojo.DesignProcedureDetails;
import com.guigu.erp.service.DesignProcedureDetailsService;
import com.guigu.erp.service.DesignProcedureService;
import com.guigu.erp.util.DesignProcedureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignProcedureServiceImpl extends ServiceImpl<DesignProcedureMapper, DesignProcedure> implements DesignProcedureService {
    @Autowired
    private DesignProcedureDetailsService designProcedureDetailsService;
    @Autowired
    private DesignProcedureMapper designProcedureMapper;
    @Override
    public PageInfo<DesignProcedure> queryPage(int pageNo, int pageSize, DesignProcedure designProcedure) {
        List<DesignProcedure> designProcedureList = null;
        QueryWrapper<DesignProcedure> queryWrapper = new QueryWrapper<>();
        if (designProcedure != null) {
            if (designProcedure.getProductName() != null && designProcedure.getProductName() != "")
                queryWrapper.like("product_name", designProcedure.getProductName());

            if (designProcedure.getCheckTag() != null && designProcedure.getCheckTag() != "")
                queryWrapper.eq("check_tag", designProcedure.getCheckTag());

            if (designProcedure.getDesignModuleTag() != null && designProcedure.getDesignModuleTag() != "")
                queryWrapper.eq("design_module_tag", designProcedure.getDesignModuleTag());

            PageHelper.startPage(pageNo, pageSize);
            designProcedureList = this.list(queryWrapper);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            designProcedureList = this.list(queryWrapper);
        }
        return new PageInfo<DesignProcedure>(designProcedureList);
    }

    @Override
    public DesignProcedureUtil designProcedureDetailsByPId(int id) {
        DesignProcedure designProcedure = this.getById(id);
        QueryWrapper<DesignProcedureDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<DesignProcedureDetails> procedureDetailsList = designProcedureDetailsService.list(wrapper);

        DesignProcedureUtil designProcedureUtil = new DesignProcedureUtil();
        designProcedureUtil.setDesignProcedure(designProcedure);
        designProcedureUtil.setDesignProcedureDetails(procedureDetailsList);
        return designProcedureUtil;
    }
    //审核通过
    @Override
    public boolean designModuleTag(DesignProcedure designProcedure) {
        DesignProcedure procedure = this.getById(designProcedure.getId());
        procedure.setChecker(designProcedure.getChecker());
        procedure.setCheckTime(designProcedure.getCheckTime());
        procedure.setCheckSuggestion(designProcedure.getCheckSuggestion());
        //1: 审核通过
        procedure.setCheckTag("1");
        int result = designProcedureMapper.updateById(procedure);
        if (result>0)
            return true;
        else
            return false;
    }

    //审核不通过
    @Override
    public boolean delete(DesignProcedure designProcedure) {

        return false;
    }

}

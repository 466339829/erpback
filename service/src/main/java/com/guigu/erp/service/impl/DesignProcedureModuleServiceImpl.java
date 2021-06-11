package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.DesignProcedureDetailsMapper;
import com.guigu.erp.mapper.DesignProcedureMapper;
import com.guigu.erp.mapper.DesignProcedureModuleMapper;
import com.guigu.erp.pojo.DesignProcedure;
import com.guigu.erp.pojo.DesignProcedureDetails;
import com.guigu.erp.pojo.DesignProcedureModule;
import com.guigu.erp.service.DesignProcedureModuleService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DesignProcedureModuleServiceImpl extends ServiceImpl<DesignProcedureModuleMapper, DesignProcedureModule> implements DesignProcedureModuleService {
    @Autowired
    private DesignProcedureDetailsMapper designProcedureDetailsMapper;
    @Autowired
    private DesignProcedureMapper designProcedureMapper;

    //添加 产品生产工序物料明细
    @Transactional
    @Override
    public ResultUtil saveBatchExtend(List<DesignProcedureModule> designProcedureModules) {
        //产品生产工序明细
        DesignProcedureDetails designProcedureDetails = designProcedureDetailsMapper.selectById(designProcedureModules.get(0).getParentId());
        //当前工序物料标志
        designProcedureDetails.setDesignModuleTag("1");
        int result1 = designProcedureDetailsMapper.updateById(designProcedureDetails);

        //产品生产工序
        DesignProcedure designProcedure = designProcedureMapper.selectById(designProcedureDetails.getParentId());
        //工序物料设计标志已提交
        designProcedure.setDesignModuleTag("1");
        int result2 = designProcedureMapper.updateById(designProcedure);

        //工序物料序号details_number+1
        int j = 1;
        for (DesignProcedureModule designProcedureModule: designProcedureModules){
            designProcedureModule.setDetailsNumber(j);
            j++;
        }
        boolean result3 = this.saveBatch(designProcedureModules);
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        if (result1==1 && result2==1 && result3==true){
            resultUtil.setResult(true);
            resultUtil.setMessage("操作成功");
            return resultUtil;
        }else {
            resultUtil.setResult(false);
            resultUtil.setMessage("操作失败");
            return resultUtil;
        }
    }

    @Override
    public List<DesignProcedureModule> selectByPId(int id) {
        QueryWrapper<DesignProcedureModule> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        return this.list(wrapper);
    }
}

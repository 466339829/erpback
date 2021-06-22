package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.DesignProcedureModuleMapper;
import com.guigu.erp.mapper.ModuleDetailsMapper;
import com.guigu.erp.pojo.DesignProcedureModule;
import com.guigu.erp.pojo.Module;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.service.ModuleDetailsService;
import com.guigu.erp.service.ModuleService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ModuleDetailsServiceImpl extends ServiceImpl<ModuleDetailsMapper, ModuleDetails> implements ModuleDetailsService {
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ModuleDetailsMapper moduleDetailsMapper;
    @Autowired
    private DesignProcedureModuleMapper designProcedureModuleMapper;
    @Override
    public ResultUtil deleteById(int id, int parentId) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        Module module = moduleService.getById(parentId);
        ModuleDetails details = this.getById(id);
        List<DesignProcedureModule> designProcedureModules = designProcedureModuleMapper.selectById(module.getProductId(),details.getProductId());
        if (designProcedureModules.size()>0){
            resultUtil.setResult(false);
            resultUtil.setMessage("操作失败，物料已存在产品工序物料明细");
            return resultUtil;
        }
        //删除
        boolean result1 = this.removeById(id);
        QueryWrapper<ModuleDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<ModuleDetails> list = this.list(queryWrapper);
        int costPriceSum=0;
        for (ModuleDetails m:list) {
            float i =(m.getCostPrice().floatValue()) * m.getAmount();
            costPriceSum+=i;
        }
        //物料总成本
        module.setCostPriceSum(BigDecimal.valueOf(costPriceSum));
        module.setCheckTag("0");
        boolean result2 = moduleService.updateById(module);
        if (result1==true && result2==true ){
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
    public List<ModuleDetails> selectByProductId(String productId) {
        return moduleDetailsMapper.selectByProductId(productId);
    }
}

package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ModuleMapper;
import com.guigu.erp.pojo.File;
import com.guigu.erp.pojo.Module;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.service.ModuleDetailsService;
import com.guigu.erp.service.ModuleService;
import com.guigu.erp.util.IDUtil;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private ModuleDetailsService moduleDetailsService;
    @Transactional
    @Override
    public ResultUtil saveBatchExtend(List<ModuleDetails> moduleDetailsList, Module module) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        //design_module_ta为1

        //物料设计单编号  200+当前日期+4位流水号
        String longId = moduleMapper.getLongId();
        String designId = IDUtil.getDesignId(longId);
        module.setDesignId(designId);
        //添加产品物料组成
        boolean result1 = this.save(module);


        int j = 1;
        for (ModuleDetails m:moduleDetailsList) {
            //parent_id与design_module的id相对应,为外键
            m.setParentId(Integer.parseInt(module.getDesignId()));

            //subtotal= cost_price*amount
            float i =(m.getCostPrice().floatValue()) * m.getAmount();
            m.setSubtotal(BigDecimal.valueOf(i));

            //对每一个产品的物料组成而言,第一个物料的details_number从1开始,
            // 每增加一个物料details_number递增1
            m.setDetailsNumber(j);
            j++;
        }
        //添加产品物料组成明细
        boolean result2 = moduleDetailsService.saveBatch(moduleDetailsList);

        if (result1==true && result2==true){
            resultUtil.setResult(true);
            resultUtil.setMessage("操作成功");
            return resultUtil;
        }else {
            resultUtil.setResult(false);
            resultUtil.setMessage("操作失败");
            return resultUtil;
        }
    }
}

package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ModuleMapper;
import com.guigu.erp.pojo.File;
import com.guigu.erp.pojo.Module;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.service.FileService;
import com.guigu.erp.service.ModuleDetailsService;
import com.guigu.erp.service.ModuleService;
import com.guigu.erp.util.IDUtil;
import com.guigu.erp.util.ListUtil;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements ModuleService {
    @Autowired
    private FileService fileService;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private ModuleDetailsService moduleDetailsService;
    //产品物料组成 + 产品物料组成明细
    @Transactional
    @Override
    public ResultUtil saveBatchExtend(List<ModuleDetails> moduleDetailsList) {
        //design_module_ta为1
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        String goodsId = moduleDetailsList.get(0).getGoodsId();
        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("product_id",goodsId);
        File file = fileService.getOne(fileQueryWrapper);
        file.setDesignModuleTag("1");
        boolean result3 = fileService.updateById(file);

        //物料设计单  编号:00+当前日期+4位流水号
        Module module = new Module();
        String longId = moduleMapper.getLongId();
        String designId = IDUtil.getDesignId(longId);

        int costPriceSum=0;
        for (ModuleDetails m:moduleDetailsList) {
            float i =(m.getCostPrice().floatValue()) * m.getAmount();
            costPriceSum+=i;
        }
        module.setDesignId(designId);
        module.setProductId(goodsId);
        module.setProductName(file.getProductName());
        module.setFirstKindId(file.getFirstKindId());
        module.setFirstKindName(file.getFirstKindName());
        module.setSecondKindId(file.getSecondKindId());
        module.setSecondKindName(file.getSecondKindName());
        module.setThirdKindId(file.getThirdKindId());
        module.setThirdKindName(file.getThirdKindName());

        module.setDesigner(moduleDetailsList.get(0).getDesigner());
        module.setModuleDescribe(moduleDetailsList.get(0).getModuleDescribe());
        module.setCostPriceSum(BigDecimal.valueOf(costPriceSum));
        module.setRegister(file.getRegister());
        module.setRegisterTime(new Date());

        module.setCheckTag("0");
        module.setChangeTag("0");
        //添加产品物料组成
        boolean result1 = this.save(module);
        int j = 1;
        for (ModuleDetails m:moduleDetailsList) {
            //parent_id与design_module的id相对应,为外键
            m.setParentId(module.getId());
            //subtotal= cost_price*amount
            float i =(m.getCostPrice().floatValue()) * m.getAmount();
            m.setSubtotal(BigDecimal.valueOf(i));
            //对每一个产品的物料组成而言,第一个物料的details_number从1开始,
            // 每增加一个物料details_number递增1
            m.setDetailsNumber(j);
            //residual_amount
            m.setResidualAmount(m.getAmount());
            j++;
        }
        //添加产品物料组成明细
        boolean result2 = moduleDetailsService.saveBatch(moduleDetailsList);

        if (result1==true && result2==true && result3==true){
            resultUtil.setResult(true);
            resultUtil.setMessage("操作成功");
            return resultUtil;
        }else {
            resultUtil.setResult(false);
            resultUtil.setMessage("操作失败");
            return resultUtil;
        }
    }

    //跟据module.id查询产品物料组成 + 产品物料组成明细
    @Override
    public ListUtil selectById(int id) {
        Map<Module, List<ModuleDetails>> map = new HashMap<Module, List<ModuleDetails>>();
        Module module = this.getById(id);
        QueryWrapper<ModuleDetails> moduleDetailsQueryWrapper = new QueryWrapper<>();
        moduleDetailsQueryWrapper.eq("parent_id",module.getId());
        List<ModuleDetails> moduleDetailsList = moduleDetailsService.list(moduleDetailsQueryWrapper);
        ListUtil listUtil = new ListUtil();
        listUtil.setModule(module);
        listUtil.setModuleDetailsList(moduleDetailsList);
        return listUtil;
    }

    @Transactional
    @Override
    public ResultUtil deleteById(int id) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        Module module = this.getById(id);
        //产品物料组成check_tag ==2
        module.setCheckTag("2");
        boolean result1 = this.updateById(module);

        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("product_id",module.getProductId());
        File file = fileService.getOne(fileQueryWrapper);
        //file 物料组成标志 0
        file.setDesignModuleTag("0");
        boolean result2 = fileService.updateById(file);


        if (result1==true && result2==true){
            resultUtil.setResult(true);
            resultUtil.setMessage("成功");
            return resultUtil;
        }else {
            resultUtil.setResult(false);
            resultUtil.setMessage("失败");
            return resultUtil;
        }
    }
    //通过审核
    @Override
    public ResultUtil checkTag(int id, String checker) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        Module module = this.getById(id);
        module.setCheckTag("1");
        module.setChecker(checker);
        module.setCheckTime(new Date());
        boolean result = this.updateById(module);
        if (result==true ){
            resultUtil.setResult(true);
            resultUtil.setMessage("成功");
            return resultUtil;
        }else {
            resultUtil.setResult(false);
            resultUtil.setMessage("失败");
            return resultUtil;
        }
    }
    //变更产品物料组成
    @Transactional
    @Override
    public ResultUtil updateBatchExtend(List<ModuleDetails> moduleDetails) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<>();
        moduleQueryWrapper.eq("design_id",moduleDetails.get(0).getDesignId());
        Module module = this.getOne(moduleQueryWrapper);
        //设计人designer
        module.setDesigner(moduleDetails.get(0).getDesigner());
        //设计要求module_describe
        module.setModuleDescribe(moduleDetails.get(0).getModuleDescribe());
        //0: 等待审核
        module.setCheckTag("0");
        //1: 已变更
        module.setChangeTag("1");
        //变更
        module.setChanger(moduleDetails.get(0).getChanger());
        //变更时间
        module.setChangeTime(new Date());
        int costPriceSum=0;
        for (ModuleDetails m:moduleDetails) {
            float i =(m.getCostPrice().floatValue()) * m.getAmount();
            costPriceSum+=i;
        }
        //物料总成本
        module.setCostPriceSum(BigDecimal.valueOf(costPriceSum));
        boolean result1 = this.updateById(module);

        //全删产品物料组成明细
        QueryWrapper<ModuleDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",module.getId());
        boolean result2 = moduleDetailsService.remove(queryWrapper);

        int j = 1;
        for (ModuleDetails m:moduleDetails) {
            //parent_id与design_module的id相对应,为外键
            m.setParentId(module.getId());
            //subtotal= cost_price*amount
            float i =(m.getCostPrice().floatValue()) * m.getAmount();
            m.setSubtotal(BigDecimal.valueOf(i));
            //对每一个产品的物料组成而言,第一个物料的details_number从1开始,
            // 每增加一个物料details_number递增1
            m.setDetailsNumber(j);
            //residual_amount
            m.setResidualAmount(m.getAmount());
            j++;
        }
        //添加产品物料组成明细
        boolean result3 = moduleDetailsService.saveBatch(moduleDetails);
        if (result1==true && result2==true && result3==true){
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

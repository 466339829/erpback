package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.mapper.ApplyMapper;
import com.guigu.erp.mapper.ManufactureMapper;
import com.guigu.erp.pojo.*;
import com.guigu.erp.service.*;
import com.guigu.erp.util.IDUtil;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManufactureServiceImpl extends ServiceImpl<ManufactureMapper, Manufacture> implements ManufactureService {
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private ManufactureMapper manufactureMapper;
    @Autowired
    private DesignProcedureDetailsService designProcedureDetailsService;
    @Autowired
    private DesignProcedureModuleService designProcedureModuleService;
    @Autowired
    private ProcedureService procedureService;
    @Autowired
    private ProcedureModuleService procedureModuleService;

    @Transactional
    @Override
    public ResultUtil addManufacture(Manufacture manufacture) {
        String[] strs = manufacture.getIds().split(",");
        int result = 0;
        int length =strs.length;
        for (String str : strs) {
            Apply apply = applyMapper.selectById(str);
            apply.setManufactureTag("1");
            applyMapper.updateById(apply);
            result ++;
        }
        String longId = manufactureMapper.getLongId();
        String manufactureId = IDUtil.getManufactureId(longId);
        manufacture.setManufactureId(manufactureId);
        boolean result1 = this.save(manufacture);

        List<DesignProcedureDetails> designProcedureDetails = designProcedureDetailsService.selectByPid(manufacture.getProductId());

        ArrayList<ProcedureModule> procedureModules = new ArrayList<>();
        int i = 1;
        int result2 = 0;
        for (DesignProcedureDetails dpd :designProcedureDetails){
            Procedure procedure = new Procedure();
            procedure.setParentId(manufacture.getId());
            procedure.setDetailsNumber(i);
            procedure.setProcedureId(dpd.getProcedureId());
            procedure.setProcedureName(dpd.getProcedureName());
            procedure.setLabourHourAmount(dpd.getLabourHourAmount().floatValue());
            procedure.setSubtotal(dpd.getSubtotal().floatValue());
            procedure.setModuleSubtotal(dpd.getModuleSubtotal().floatValue());
            procedure.setCostPrice(dpd.getCostPrice().floatValue());
            procedure.setDemandAmount(manufacture.getAmount());
            procedure.setProcedureFinishTag("0");
            procedure.setProcedureTransferTag("0");

            List<DesignProcedureModule> designProcedureModules = designProcedureModuleService.selectByPId(dpd.getId());

            boolean save = procedureService.save(procedure);
            result2 ++;
            int j = 1;
            for (DesignProcedureModule dpm: designProcedureModules){
                ProcedureModule procedureModule = new ProcedureModule();
                procedureModule.setParentId(procedure.getId());
                procedureModule.setDetailsNumber(j);
                procedureModule.setProductId(dpm.getProductId());
                procedureModule.setProductName(dpm.getProductName());
                procedureModule.setCostPrice(dpm.getCostPrice().floatValue());
                procedureModule.setAmount(manufacture.getAmount());
                procedureModule.setRealAmount(0f);
                procedureModule.setRenewAmount(0f);
                procedureModule.setSubtotal(dpm.getSubtotal().floatValue());
                procedureModule.setRealSubtotal(0f);

                procedureModules.add(procedureModule);
                j++;
            }
            i++;
        }
        boolean result3 = procedureModuleService.saveBatch(procedureModules);
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        if (result == length && result1==true && result2 ==designProcedureDetails.size() && result3==true){
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
    public PageInfo<Manufacture> queryPage(int pageNo, int pageSize, Manufacture manufacture) {
        List<Manufacture> manufactureList = null;
        QueryWrapper<Manufacture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        if (manufacture != null) {

            if (manufacture.getManufactureId() != null && manufacture.getManufactureId() != "")
                queryWrapper.eq("manufacture_id", manufacture.getManufactureId());
            if (manufacture.getProductName() != null && manufacture.getProductName() != "")
                queryWrapper.like("product_name", manufacture.getProductName());
            if (manufacture.getProductName() != null && manufacture.getProductName() != "")
                queryWrapper.like("product_name", manufacture.getProductName());

            // 追加条件 建档时间开始
            if (manufacture.getRegisterTime() != null)
                queryWrapper.ge("register_time", manufacture.getRegisterTime());
            // 追加条件 建档时间结束
            if (manufacture.getRegisterTime2() != null )
                queryWrapper.le("register_time", manufacture.getRegisterTime2());



            PageHelper.startPage(pageNo, pageSize);
            manufactureList = this.list(queryWrapper);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            manufactureList = this.list(queryWrapper);
        }
        return new PageInfo<Manufacture>(manufactureList);
    }
}

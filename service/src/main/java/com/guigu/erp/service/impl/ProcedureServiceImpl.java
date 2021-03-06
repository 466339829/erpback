package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.GatherMapper;
import com.guigu.erp.mapper.ProcedureMapper;
import com.guigu.erp.pojo.*;
import com.guigu.erp.service.*;
import com.guigu.erp.util.IDUtil;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ProcedureServiceImpl extends ServiceImpl<ProcedureMapper, Procedure> implements ProcedureService {
    @Autowired
    private ProcedureModuleService procedureModuleService;
    @Autowired
    private ProcedureMapper procedureMapper;
    @Autowired
    private ManufactureService manufactureService;
    @Autowired
    private GatherMapper gatherMapper;
   @Autowired
   private GathService gathService;
   @Autowired
   private FileService fileService;
   @Autowired
   private GatherDetailsService gatherDetailsService;
    @Override
    public List<Procedure> selectByPid(int parentId) {
        QueryWrapper<Procedure> procedureQueryWrapper = new QueryWrapper<>();
        procedureQueryWrapper.eq("parent_id",parentId);
        return this.list(procedureQueryWrapper);
    }

    @Transactional
    @Override
    public ResultUtil finishTag(int id, String procedureFinishTag) {
        ResultUtil<Object> resultUtil = new ResultUtil<Object>();
        if ("1".equals(procedureFinishTag)){
           /* QueryWrapper<ProcedureModule> procedureModuleQueryWrapper = new QueryWrapper<>();
            procedureModuleQueryWrapper.eq("parent_id",id);*/
            /*List<ProcedureModule> list = procedureModuleService.list(procedureModuleQueryWrapper);*/
           /* int i = 0;
            for (ProcedureModule procedureModule:list){
                if (!procedureModule.getAmount().equals(procedureModule.getRealAmount())){
                    i++;
                }
            }*/
            /*if (i>0){
                resultUtil.setResult(false);
                resultUtil.setMessage("???????????????");
                return resultUtil;
            }else {*/
                boolean result2 = procedureMapper.updateFinishTag(id,"1");
                resultUtil.setResult(result2);
                resultUtil.setMessage("????????????");
                return resultUtil;
        }
        if ("2".equals(procedureFinishTag)){
            boolean result2 = procedureMapper.updateFinishTag(id,"2");
            resultUtil.setResult(result2);
            resultUtil.setMessage("????????????");
            return resultUtil;
        }
        return null;
    }
    //??????
    @Transactional
    @Override
    public boolean transferTag(int id, Float realAmount) {
        Procedure procedure = this.getById(id);
        procedure.setRealAmount(realAmount);
        //procedure_transfer_tag 1 ?????????
        procedure.setProcedureTransferTag("1");
        Manufacture manufacture = manufactureService.getById(procedure.getParentId());
        manufacture.setManufactureProcedureTag("1");
        boolean result1 = manufactureService.updateById(manufacture);
        boolean result2 = this.updateById(procedure);
        if (result1&&result2)
            return true;
        return false;
    }

    //????????????
    @Transactional
    @Override
    public boolean checkTransferTag(int id) {
        Procedure procedure = this.getById(id);
        //procedure_transfer_tag 2 ?????????
        procedure.setProcedureTransferTag("2");
        boolean result1 = this.updateById(procedure);

        QueryWrapper<Procedure> procedureQueryWrapper = new QueryWrapper<>();
        procedureQueryWrapper.eq("parent_id",procedure.getParentId());

        //????????????
        Manufacture manufacture = manufactureService.getById(procedure.getParentId());
        List<Procedure> procedureList = this.list(procedureQueryWrapper);
        int i = 0;
        for (Procedure p:procedureList){
            if ("2".equals(p.getProcedureTransferTag())){
                i++;
            }
        }
        if (i== procedureList.size()){
            //??????????????????
            manufacture.setManufactureProcedureTag("2");
            manufacture.setTestedAmount(manufacture.getAmount());
            boolean result2 = manufactureService.updateById(manufacture);
            //????????????
            String longId = gatherMapper.LongId();
            String gatherId = IDUtil.getGatherId(longId);
            Gather gather = new Gather();
            gather.setGatherId(gatherId);
            gather.setReason("????????????");
            gather.setStorer(manufacture.getDesigner());
            gather.setReasonexact(manufacture.getApplyIdGroup());
            /*amount_sum	number(12,2)	?????????*/
            gather.setAmountSum(BigDecimal.valueOf(manufacture.getAmount().floatValue()));
            /* cost_price_sum	number(12,2)	?????????*/
            gather.setCostPriceSum(BigDecimal.valueOf(manufacture.getRealModuleCostPriceSum().floatValue()));
            /*gathered_amount_sum	number(12,2)	?????????????????????*/
            gather.setGatheredAmountSum(BigDecimal.valueOf(manufacture.getTestedAmount().floatValue()));
            /*remark	varchar2(400)	??????*/
            gather.setRemark(manufacture.getRemark());
           /* register	varchar2(50)	?????????*/
            gather.setRegister(manufacture.getRegister());
           /* register_time	date	????????????*/
            gather.setRegisterTime(new Date());
            /*checker	varchar2(50)	?????????*/
            gather.setChecker(manufacture.getChecker());
            /*check_time	date	????????????*/
            gather.setCheckTime(manufacture.getCheckTime());
            /*check_tag	varchar2(20)	????????????*/
            gather.setCheckTag("s001-1");
            /*store_tag	varchar2(20)	????????????*/
            gather.setGatherTag("k002-1");
            boolean result3 = gathService.save(gather);

            QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
            fileQueryWrapper.eq("product_id",manufacture.getProductId());
            File file = fileService.getOne(fileQueryWrapper);

            //????????????(s_gather_details)
            GatherDetails gatherDetails = new GatherDetails();
            gatherDetails.setParentId(gather.getId());
            gatherDetails.setProductId(manufacture.getProductId());
            gatherDetails.setProductName(manufacture.getProductName());
            gatherDetails.setProductDescribe(manufacture.getProductDescribe());
            gatherDetails.setAmount(BigDecimal.valueOf(manufacture.getAmount().floatValue()));
            gatherDetails.setAmountUnit(file.getAmountUnit());
            gatherDetails.setCostPrice(BigDecimal.valueOf(file.getCostPrice().floatValue()));
            gatherDetails.setSubtotal(BigDecimal.valueOf(manufacture.getAmount()*file.getCostPrice().floatValue()));
            /*gather_tag	varchar2(20)	???????????? k002-1: ?????????*/
            gatherDetails.setGatherTag("k002-1");
            boolean result4 = gatherDetailsService.save(gatherDetails);


            if(result1==true && result2 ==true && result3 ==true && result4 ==true)
                return true;
            return false;
        }else {
            manufacture.setManufactureProcedureTag("0");
            boolean result2 = manufactureService.updateById(manufacture);
            if(result1==true && result2 ==true)
                return true;
            return false;
        }
    }
}

package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.mapper.ApplyMapper;
import com.guigu.erp.mapper.FileMapper;
import com.guigu.erp.mapper.ManufactureMapper;
import com.guigu.erp.mapper.PayMapper;
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
    @Autowired
    private PayMapper payMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private PayDetailsService payDetailsService;
    @Autowired
    private PayService payService;

    @Transactional
    @Override
    public ResultUtil addManufacture(Manufacture manufacture) {
        String[] strs = manufacture.getIds().split(",");
        int result = 0;
        int length = strs.length;
        for (String str : strs) {
            Apply apply = applyMapper.selectById(str);
            apply.setManufactureTag("1");
            applyMapper.updateById(apply);
            result++;
        }
        String longId = manufactureMapper.getLongId();
        String manufactureId = IDUtil.getManufactureId(longId);
        manufacture.setManufactureId(manufactureId);
        boolean result1 = this.save(manufacture);

        List<DesignProcedureDetails> designProcedureDetails = designProcedureDetailsService.selectByPid(manufacture.getProductId());

        ArrayList<ProcedureModule> procedureModules = new ArrayList<>();
        int i = 1;
        int result2 = 0;
        for (DesignProcedureDetails dpd : designProcedureDetails) {
            Procedure procedure = new Procedure();
            procedure.setParentId(manufacture.getId());
            procedure.setDetailsNumber(i);
            procedure.setProcedureId(dpd.getProcedureId());
            procedure.setProcedureName(dpd.getProcedureName());
            procedure.setLabourHourAmount(dpd.getLabourHourAmount().floatValue()*manufacture.getAmount());
            procedure.setSubtotal(dpd.getSubtotal().floatValue()*manufacture.getAmount());
            procedure.setModuleSubtotal(dpd.getModuleSubtotal().floatValue()*manufacture.getAmount());
            procedure.setCostPrice(dpd.getCostPrice().floatValue());
            procedure.setDemandAmount(manufacture.getAmount());
            procedure.setProcedureFinishTag("0");
            procedure.setProcedureTransferTag("0");
            procedure.setRealSubtotal(0f);
            procedure.setRealLabourHourAmount(0f);
            procedure.setRealModuleSubtotal(0f);
            procedure.setRealAmount(0f);

            List<DesignProcedureModule> designProcedureModules = designProcedureModuleService.selectByPId(dpd.getId());

            boolean save = procedureService.save(procedure);
            result2++;
            int j = 1;
            for (DesignProcedureModule dpm : designProcedureModules) {
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
        if (result == length && result1 == true && result2 == designProcedureDetails.size() && result3 == true) {
            resultUtil.setResult(true);
            resultUtil.setMessage("操作成功");
            return resultUtil;
        } else {
            resultUtil.setResult(false);
            resultUtil.setMessage("操作失败");
            return resultUtil;
        }
    }

    @Override
    public PageInfo<Manufacture> queryPage(int pageNo, int pageSize, Manufacture manufacture) {
        List<Manufacture> manufactureList = null;
        QueryWrapper<Manufacture> queryWrapper = new QueryWrapper<>();
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
            if (manufacture.getRegisterTime2() != null)
                queryWrapper.le("register_time", manufacture.getRegisterTime2());
            if (manufacture.getCheckTag() != null && manufacture.getCheckTag() != "")
                queryWrapper.le("check_tag", manufacture.getCheckTag());

            PageHelper.startPage(pageNo, pageSize);
            manufactureList = this.list(queryWrapper);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            manufactureList = this.list(queryWrapper);
        }
        return new PageInfo<Manufacture>(manufactureList);
    }

    @Transactional
    @Override
    public ResultUtil checkTag(Manufacture m) {
        ResultUtil<Users> resultUtil = new ResultUtil<>();
        Manufacture manufacture = this.getById(m.getId());
        manufacture.setChecker(m.getChecker());
        manufacture.setCheckTime(m.getCheckTime());
        //审核不通过
        if ("2".equals(m.getCheckTag())) {
            manufacture.setCheckTag("2");
            boolean result1 = this.updateById(manufacture);
            if (result1 == true) {
                resultUtil.setMessage("成功");
                resultUtil.setResult(true);
                return resultUtil;
            } else {
                resultUtil.setMessage("失败");
                resultUtil.setResult(false);
                return resultUtil;
            }
        }
        //审核通过
        if ("1".equals(m.getCheckTag())) {
            manufacture.setCheckTag("1");
            //审核 通过
            boolean result1 = this.updateById(manufacture);

            //查询所有工序
            QueryWrapper<Procedure> procedureQueryWrapper = new QueryWrapper<>();
            procedureQueryWrapper.eq("parent_id", manufacture.getId());
            List<Procedure> procedureList = procedureService.list(procedureQueryWrapper);

            List<PayDetails> payDetailsList = new ArrayList<>();
            //出库
            int i = 0;
            int j = 0;
            for (Procedure procedure : procedureList) {
                Pay pay = new Pay();
                String longId = payMapper.getLongId();
                String payId = IDUtil.getPayId(longId);
                pay.setPayId(payId);
                pay.setStorer(manufacture.getChecker());
                pay.setReason("1");//生产领料
                //出库详细理由 派工单1-组装
                pay.setReasonexact(manufacture.getManufactureId() + procedure.getProcedureName());
                pay.setAmountSum(manufacture.getAmount());//amount
                pay.setCostPriceSum(procedure.getSubtotal() + procedure.getModuleSubtotal());
                pay.setPaidAmountSum(0f);
                pay.setCheckTag("1");
                pay.setPayTag("1");

                //添加出库
                boolean save = payService.save(pay);
                i++;
                //查询工序的所有物料
                QueryWrapper<ProcedureModule> procedureModuleQueryWrapper = new QueryWrapper<>();
                procedureModuleQueryWrapper.eq("parent_id",procedure.getId());
                List<ProcedureModule> procedureModuleList = procedureModuleService.list(procedureModuleQueryWrapper);

                for (ProcedureModule procedureModule:procedureModuleList){
                    PayDetails payDetails = new PayDetails();
                    payDetails.setParentId(pay.getId());
                    payDetails.setProductId(procedureModule.getProductId());
                    payDetails.setProductName(procedureModule.getProductName());
                    payDetails.setAmount(manufacture.getAmount());
                    payDetails.setCostPrice(procedureModule.getCostPrice());
                    payDetails.setSubtotal(manufacture.getAmount()*procedureModule.getCostPrice());
                    payDetails.setPaidAmount(0f);
                    payDetails.setPayTag("1");

                    QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
                    fileQueryWrapper.eq("product_id",procedureModule.getProductId());
                    File file = fileMapper.selectOne(fileQueryWrapper);
                    //单位
                    payDetails.setAmountUnit(file.getPersonalUnit());

                    payDetailsList.add(payDetails);
                    j++;
                }
            }
            boolean result3 = payDetailsService.saveBatch(payDetailsList);

            if (result1 == true && i==procedureList.size() && j==payDetailsList.size()) {
                resultUtil.setMessage("成功");
                resultUtil.setResult(true);
                return resultUtil;
            } else {
                resultUtil.setMessage("失败");
                resultUtil.setResult(false);
                return resultUtil;
            }
        }
        return null;
    }
}

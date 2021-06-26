package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ProcedureModuleMapper;
import com.guigu.erp.mapper.ProcedureModulingMapper;
import com.guigu.erp.pojo.*;
import com.guigu.erp.service.*;
import com.guigu.erp.util.ProcedureModuleUtil;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcedureModuleServiceImpl extends ServiceImpl<ProcedureModuleMapper, ProcedureModule> implements ProcedureModuleService {
    @Autowired
    private ProcedureService procedureService;
    @Autowired
    private ManufactureService manufactureService;
    @Autowired
    private ProceduringService proceduringService;
    @Autowired
    private ProcedureModulingService procedureModulingService;
    @Autowired
    private ProcedureModulingMapper procedureModulingMapper;
    @Autowired
    private FileService fileService;
    @Override
    public List<ProcedureModule> selectByPid(int parentId) {
        QueryWrapper<ProcedureModule> procedureModuleQueryWrapper = new QueryWrapper<>();
        procedureModuleQueryWrapper.eq("parent_id", parentId);
        List<ProcedureModule> list = this.list(procedureModuleQueryWrapper);
        for (ProcedureModule pm:list){
            QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
            fileQueryWrapper.eq("product_id",pm.getProductId());
            File file = fileService.getOne(fileQueryWrapper);
            pm.setAmountUnit(file.getPersonalUnit());
        }
        return list;
    }
    //登记
    @Override
    @Transactional
    public ResultUtil saveOrUpdateBatchExtend(List<ProcedureModule> procedureModuleList) {
        ProcedureModule procedureModule = procedureModuleList.get(0);
        Procedure procedure = procedureService.getById(procedureModule.getParentId());
        //real_subtotal  实际工时成本
        float RealSubtotal = (procedure.getSubtotal() / procedure.getLabourHourAmount()) * procedureModule.getLabourHourAmount();
        //real_module_subtotal 实际物料成本
        float realModuleSubtotal = 0;
        for (ProcedureModule pm : procedureModuleList) {
            realModuleSubtotal += pm.getLabourHourAmount() * pm.getCostPrice();
        }



       /* //real_labour_hour_amount 实际工时数
        procedure.setRealLabourHourAmount(procedure.getRealLabourHourAmount() + procedureModule.getLabourHourAmount().floatValue());
      *//*  //real_subtotal  实际工时成本
        float RealSubtotal = (procedure.getSubtotal() / procedure.getLabourHourAmount()) * procedureModule.getLabourHourAmount();
      *//*  procedure.setRealSubtotal(procedure.getRealSubtotal() + RealSubtotal);

        procedure.setRealModuleSubtotal(procedure.getRealLabourHourAmount() + realModuleSubtotal);
      */
        procedure.setProcedureFinishTag("0");
        boolean result1 = procedureService.updateById(procedure);

        //生产总表
        Manufacture manufacture = manufactureService.getById(procedure.getParentId());


        QueryWrapper<Proceduring> proceduringQueryWrapper = new QueryWrapper<>();
        proceduringQueryWrapper.eq("parent_id",manufacture.getId());
        proceduringQueryWrapper.eq("procedure_id",procedure.getProcedureId());
                //一个SQL语句写的子查询
        proceduringQueryWrapper.inSql("reg_count","select max(reg_count) from m_proceduring");
        Proceduring one = proceduringService.getOne(proceduringQueryWrapper);

        // 生产工序过程记录(m_proceduring)
        Proceduring proceduring = new Proceduring();
        proceduring.setParentId(manufacture.getId());
        //details_number 工序序号
        proceduring.setDetailsNumber(procedure.getDetailsNumber());
        //procedure_id 工序编号
        proceduring.setProcedureId(procedure.getProcedureId());
        //procedure_name 工序名称
        proceduring.setProcedureName(procedure.getProcedureName());
        //labour_hour_amount 本次工时数
        proceduring.setLabourHourAmount(procedureModule.getLabourHourAmount().floatValue());
        //cost_price 单位工时成本
        proceduring.setCostPrice(RealSubtotal);
        //subtotal 工时成本小计
        proceduring.setSubtotal(realModuleSubtotal);
        //procedure_describe
        proceduring.setProcedureDescribe("");
        //reg_count 登记次数
        if (one==null) {
            proceduring.setRegCount(1f);
        }else {
            proceduring.setRegCount(one.getRegCount()+1);
        }
        //procedure_responsible_person 负责人
        proceduring.setProcedureResponsiblePerson(procedureModule.getProcedureResponsiblePerson());
        //register登记人
        proceduring.setRegister(procedureModule.getRegister());
        //register_time 登记时间
        proceduring.setRegisterTime(procedureModule.getRegisterTime());

        boolean result2 = proceduringService.save(proceduring);

        int result3 = 0;
        ArrayList<ProcedureModuling> procedureModulings = new ArrayList<>();
        //生产工序物料表(m_procedure_module)
        for (ProcedureModule pm : procedureModuleList) {
           /* //real_amount 实际使用数量
            pm.setRealAmount(pm.getRealAmount() + pm.getShuliang());
            //real_subtotal  实际物料成本小计
            pm.setRealSubtotal(pm.getRealSubtotal() + (pm.getCostPrice() * pm.getShuliang()));*/
           /* boolean b = this.updateById(pm);*/
            //生产工序物料过程记录(m_procedure_moduling)
            ProcedureModuling procedureModuling = new ProcedureModuling();
            //parent_id父级序号
            procedureModuling.setParentId(proceduring.getId());
            //details_number 本工序物料序号
            procedureModuling.setDetailsNumber(pm.getDetailsNumber());
            //product_id	产品编号
            procedureModuling.setProductId(pm.getProductId());
            //product_name		产品名称
            procedureModuling.setProductName(pm.getProductName());
            //cost_price		单位物料成本
            procedureModuling.setCostPrice(pm.getCostPrice());
            //amount		本次使用数量
            procedureModuling.setAmount(pm.getShuliang().floatValue());
            //subtotal		物料成本小计
            procedureModuling.setSubtotal(pm.getCostPrice() * pm.getShuliang().floatValue());
            procedureModulings.add(procedureModuling);
            result3++;
        }
        boolean result4 = procedureModulingService.saveBatch(procedureModulings);

        QueryWrapper<Procedure> procedureQueryWrapper = new QueryWrapper<>();
        procedureQueryWrapper.eq("parent_id",manufacture.getId());
        List<Procedure> list = procedureService.list(procedureQueryWrapper);
        /*//real_module_cost_price_sum 实际物料总成本
        float realModuleCostPriceSum = 0;
        //real_labour_cost_price_sum	实际工时总成本
        float  realLabourCostPriceSum= 0;
        for (Procedure p :list){
            realModuleCostPriceSum+=p.getRealModuleSubtotal();
            realLabourCostPriceSum+=p.getRealSubtotal();
        }
        manufacture.setRealModuleCostPriceSum(manufacture.getRealModuleCostPriceSum()+realModuleCostPriceSum);
        manufacture.setRealLabourCostPriceSum(manufacture.getRealLabourCostPriceSum()+realLabourCostPriceSum);
    */    //manufacture_procedure_tag 1
        manufacture.setManufactureProcedureTag("1");
        boolean result5 = manufactureService.updateById(manufacture);
        ResultUtil<Object> resultUtil = new ResultUtil<Object>();
        if (result1 ==true && result2==true && result3==procedureModuleList.size()&& result4==true && result5 ==true){
            resultUtil.setResult(true);
            resultUtil.setMessage("成功");
            return resultUtil;
        }else {
            resultUtil.setResult(false);
            resultUtil.setMessage("失败");
            return resultUtil;
        }
    }

    @Override
    public ProcedureModuleUtil selectShuLiang(int parentId) {
        Procedure procedure = procedureService.getById(parentId);

        List<ProcedureModuling> modulingList = procedureModulingMapper.selectByPid(procedure.getProcedureId(), procedure.getParentId());

        Proceduring proceduring = proceduringService.getById(modulingList.get(0).getParentId());


        QueryWrapper<ProcedureModuling> procedureModulingQueryWrapper = new QueryWrapper<>();
        procedureModulingQueryWrapper.eq("parent_id",proceduring.getId());
        List<ProcedureModuling> procedureModulings = procedureModulingService.list(procedureModulingQueryWrapper);


        QueryWrapper<ProcedureModule> procedureModuleQueryWrapper = new QueryWrapper<>();
        procedureModuleQueryWrapper.eq("parent_id",parentId);
        List<ProcedureModule> list = this.list(procedureModuleQueryWrapper);

        for (ProcedureModuling pmi:procedureModulings){
            for (ProcedureModule pm:list){
                if (pmi.getProductId().equals(pm.getProductId())){
                    pm.setShuliang(pmi.getAmount().intValue());
                    continue;
                }
            }
        }
        ProcedureModuleUtil procedureModuleUtil = new ProcedureModuleUtil();
        procedureModuleUtil.setProceduring(proceduring);
        procedureModuleUtil.setList(list);
        return procedureModuleUtil;
    }

    //登记审核
    @Override
    @Transactional
    public ResultUtil checkProcedureFinish(List<ProcedureModule> procedureModuleList) {
        //审核人 审核时间
        ProcedureModule procedureModule = procedureModuleList.get(0);
        Proceduring proceduring = proceduringService.getById(procedureModule.getProceduringId());
        proceduring.setChecker(procedureModule.getChecker());
        proceduring.setCheckTime(procedureModule.getCheckTime());
        boolean result1 = proceduringService.updateById(proceduring);


        Procedure procedure = procedureService.getById(procedureModule.getParentId());
        //real_labour_hour_amount 实际工时数
        procedure.setRealLabourHourAmount(procedure.getRealLabourHourAmount() + procedureModule.getLabourHourAmount().floatValue());
        //real_subtotal  实际工时成本
        float RealSubtotal = ((procedure.getSubtotal() / procedure.getLabourHourAmount())) * procedureModule.getLabourHourAmount();
        //real_module_subtotal 实际物料成本
        float realModuleSubtotal = 0;
        for (ProcedureModule pm : procedureModuleList) {
            realModuleSubtotal += pm.getShuliang() * pm.getCostPrice();
            System.out.println(pm);
        }
        System.out.println(realModuleSubtotal);
        //real_subtotal  实际工时成本
        procedure.setRealSubtotal(procedure.getRealSubtotal() + RealSubtotal);

        procedure.setRealModuleSubtotal(procedure.getRealModuleSubtotal() + realModuleSubtotal);
        //审核 未完成2 变 0: 未开始（登记）
        if("2".equals(procedure.getProcedureFinishTag()))
            procedure.setProcedureFinishTag("0");
        //完成- 3: 已审核（完成）
       else if("1".equals(procedure.getProcedureFinishTag()))
                procedure.setProcedureFinishTag("3");

        boolean result2 = procedureService.updateById(procedure);

        int result3 = 0;
        ArrayList<ProcedureModuling> procedureModulings = new ArrayList<>();

        for (ProcedureModule pm : procedureModuleList) {
            //real_amount 实际使用数量
            pm.setRealAmount(pm.getRealAmount() + pm.getShuliang());
            //real_subtotal  实际物料成本小计
            pm.setRealSubtotal(pm.getRealSubtotal() + (pm.getCostPrice() * pm.getShuliang()));
            boolean b = this.updateById(pm);
            result3++;
        }
        //生产总表
        Manufacture manufacture = manufactureService.getById(procedure.getParentId());

        QueryWrapper<Procedure> procedureQueryWrapper = new QueryWrapper<>();
        procedureQueryWrapper.eq("parent_id",manufacture.getId());
        List<Procedure> list = procedureService.list(procedureQueryWrapper);
        //real_module_cost_price_sum 实际物料总成本
        float realModuleCostPriceSum = 0;
        //real_labour_cost_price_sum	实际工时总成本
        float  realLabourCostPriceSum= 0;
        int tag = 0;
        for (Procedure p :list){
            realModuleCostPriceSum+=p.getRealModuleSubtotal();
            realLabourCostPriceSum+=p.getRealSubtotal();
            if (!"2".equals(p.getProcedureTransferTag())){
                tag++;
            }
        }
        manufacture.setRealModuleCostPriceSum(manufacture.getRealModuleCostPriceSum()+realModuleCostPriceSum);
        manufacture.setRealLabourCostPriceSum(manufacture.getRealLabourCostPriceSum()+realLabourCostPriceSum);
       //manufacture_procedure_tag 1
        //待登记
        if (tag>0){
            manufacture.setManufactureProcedureTag("0");
        }else if (tag==0){
            manufacture.setManufactureProcedureTag("2");
            manufacture.setTestedAmount(manufacture.getAmount());
        }
        boolean result4 = manufactureService.updateById(manufacture);

        ResultUtil<Object> resultUtil = new ResultUtil<Object>();
        if (result1 ==true && result2==true && result3==procedureModuleList.size()&& result4==true){
            resultUtil.setResult(true);
            resultUtil.setMessage("成功");
            return resultUtil;
        }else {
            resultUtil.setResult(false);
            resultUtil.setMessage("失败");
            return resultUtil;
        }
    }
}

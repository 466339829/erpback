package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ProcedureModuleMapper;
import com.guigu.erp.pojo.*;
import com.guigu.erp.service.*;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ProcedureModule> selectByPid(int parentId) {
        QueryWrapper<ProcedureModule> procedureModuleQueryWrapper = new QueryWrapper<>();
        procedureModuleQueryWrapper.eq("parent_id", parentId);
        return this.list(procedureModuleQueryWrapper);
    }

    @Override
    public ResultUtil saveOrUpdateBatchExtend(List<ProcedureModule> procedureModuleList) {
        ProcedureModule procedureModule = procedureModuleList.get(0);
        Procedure procedure = procedureService.getById(procedureModule.getParentId());
        //real_labour_hour_amount 实际工时数
        procedure.setRealLabourHourAmount(procedure.getRealLabourHourAmount() + procedureModule.getLabourHourAmount().floatValue());
        //real_subtotal  实际工时成本
        float RealSubtotal = (procedure.getSubtotal() / procedure.getLabourHourAmount()) * procedureModule.getLabourHourAmount();
        procedure.setRealSubtotal(procedure.getRealSubtotal() + RealSubtotal);
        //real_module_subtotal 实际物料成本
        float realModuleSubtotal = 0;
        for (ProcedureModule pm : procedureModuleList) {
            realModuleSubtotal += pm.getLabourHourAmount() * pm.getCostPrice();
        }
        procedure.setRealModuleSubtotal(procedure.getRealLabourHourAmount() + realModuleSubtotal);
        boolean result1 = procedureService.updateById(procedure);

        //生产总表
        Manufacture manufacture = manufactureService.getById(procedure.getParentId());

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
        proceduring.setRegCount(1f);
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
            //real_amount 实际使用数量
            pm.setRealAmount(pm.getRealAmount() + pm.getShuliang());
            //real_subtotal  实际物料成本小计
            pm.setRealSubtotal(pm.getRealSubtotal() + (pm.getCostPrice() * pm.getShuliang()));
            boolean b = this.updateById(pm);
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

package com.guigu.erp.controller;

import com.guigu.erp.pojo.Procedure;
import com.guigu.erp.service.ProcedureService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/procedure")
public class ProcedureController {
    @Autowired
    private ProcedureService procedureService;

    @RequestMapping("/selectByPid/{parentId}")
    public List<Procedure> selectByPid(@PathVariable int parentId){
        return procedureService.selectByPid(parentId);
    }

    @RequestMapping("/finishTag")
    public ResultUtil finishTag(int id,String procedureFinishTag){
        return procedureService.finishTag(id,procedureFinishTag);
    }
    //交接登记
    @RequestMapping("/transferTag")
    public boolean ransferTag(int id,Float realAmount){
        return procedureService.transferTag(id,realAmount);
    }
    //交接登记审核
    @RequestMapping("/checkTransferTag/{id}")
    public boolean checkTransferTag(@PathVariable int id){
        return procedureService.checkTransferTag(id);
    }
}

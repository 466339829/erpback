package com.guigu.erp.controller;

import com.guigu.erp.pojo.Procedure;
import com.guigu.erp.service.ProcedureService;
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
}

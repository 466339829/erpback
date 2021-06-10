package com.guigu.erp.controller;

import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.DesignProcedure;
import com.guigu.erp.service.DesignProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/designProcedure")
public class DesignProcedureController {
    @Autowired
    private DesignProcedureService designProcedureService;

    /**
     * 条件分页查询
     * @param pageNo
     * @param pageSize
     * @param designProcedure
     * @return
     */
    @RequestMapping(value = "/page", produces = "application/json;charset=utf-8")
    public PageInfo<DesignProcedure> page(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                                          DesignProcedure designProcedure) {
        return designProcedureService.queryPage(pageNo, pageSize, designProcedure);
    }
}

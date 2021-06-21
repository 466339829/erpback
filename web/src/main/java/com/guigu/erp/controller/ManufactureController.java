package com.guigu.erp.controller;

import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Manufacture;
import com.guigu.erp.service.ManufactureService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manufacture")
public class ManufactureController {
    @Autowired
    private ManufactureService manufactureService;
    /**
     * 新增
     *
     * @param manufacture
     * @return
     */
    @RequestMapping(value = "/insert", produces = "application/json;charset=utf-8")
    public ResultUtil insert(Manufacture manufacture) {
        return manufactureService.addManufacture(manufacture);
    }
    //条件分页查询
    @RequestMapping("/page")
    public PageInfo<Manufacture> page(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                      @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                      Manufacture manufacture){

        return  manufactureService.queryPage(pageNo,pageSize,manufacture);
    }
}

package com.guigu.erp.controller;

import com.guigu.erp.service.ModuleDetailsService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moduleDetails")
public class ModuleDetailsController {
    @Autowired
    private ModuleDetailsService moduleDetailsService;

    /**
     * 不通过
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public ResultUtil delete(int id, int parentId){
        return  moduleDetailsService.deleteById(id,parentId);
    }
}

package com.guigu.erp.controller;

import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Gather;
import com.guigu.erp.service.GatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gather")
public class GatherController {
    @Autowired
    private GatherService gatherService;

    @RequestMapping("/page")
    public PageInfo<Gather> page(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                 Gather gather) {
        return gatherService.queryPage(pageNo, pageSize, gather);
    }
}

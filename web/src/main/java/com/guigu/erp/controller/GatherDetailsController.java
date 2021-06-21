package com.guigu.erp.controller;

import com.guigu.erp.pojo.GatherDetails;
import com.guigu.erp.service.GatherDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gath")
public class GatherDetailsController {

    @Autowired
    GatherDetailsService gatherDetailsService;

    @RequestMapping("/gathset")
    public Boolean gathset(@RequestBody List<GatherDetails> gatherDetails){
       return gatherDetailsService.saveBatchExtend(gatherDetails);
    }
}

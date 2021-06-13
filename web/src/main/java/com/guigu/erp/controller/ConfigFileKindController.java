package com.guigu.erp.controller;

import com.guigu.erp.pojo.ConfigFileKind;
import com.guigu.erp.service.ConfigFileKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/configFileKind")
public class ConfigFileKindController {
    @Autowired
    private ConfigFileKindService configFileKindService;

    @RequestMapping("/list")
    public List<ConfigFileKind> list(){
        return configFileKindService.list();
    }
}

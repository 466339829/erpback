package com.guigu.erp.controller;

import com.guigu.erp.pojo.Proceduring;
import com.guigu.erp.service.ProceduringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/proceduring")
public class ProceduringConroller {
    @Autowired
    private ProceduringService proceduringService;

    @RequestMapping("/selectByPid/{pid}")
    public List<Proceduring> selectByPid(@PathVariable int pid){
        return proceduringService.selectByPid(pid);
    }
}

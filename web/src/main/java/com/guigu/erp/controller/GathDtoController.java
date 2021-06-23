package com.guigu.erp.controller;

import com.github.pagehelper.PageInfo;
import com.guigu.erp.GathDto;
import com.guigu.erp.service.GathDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/GathDtos")
public class GathDtoController {
    @Autowired
    GathDtoService gathDtoService;

    @RequestMapping("/lisat")
    public List<GathDto> ae(int id) {


        return gathDtoService.gathShenhe(id);
    }
}

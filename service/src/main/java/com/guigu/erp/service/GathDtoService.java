package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.GathDto;
import com.guigu.erp.pojo.Gather;

import java.util.List;

public interface GathDtoService extends IService<GathDto> {
    List<GathDto> gathShenhe(int id);
}

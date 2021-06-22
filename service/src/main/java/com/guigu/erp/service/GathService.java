package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Gather;

public interface GathService extends IService<Gather> {

int updates(Gather gather);
}

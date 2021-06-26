package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Gather;

public interface GatherService extends IService<Gather> {
    PageInfo<Gather> queryPage(int pageNo, int pageSize, Gather gather);
}

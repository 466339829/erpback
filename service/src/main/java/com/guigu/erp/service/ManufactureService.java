package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Manufacture;
import com.guigu.erp.util.ResultUtil;

public interface ManufactureService extends IService<Manufacture> {
    ResultUtil addManufacture(Manufacture manufacture);

    PageInfo<Manufacture> queryPage(int pageNo, int pageSize, Manufacture manufacture);
}

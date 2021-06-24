package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Pay;

public interface PayService extends IService<Pay> {
    PageInfo<Pay> page(Integer pageNO,Integer pageSize);
}

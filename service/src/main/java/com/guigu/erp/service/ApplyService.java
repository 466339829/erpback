package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.Apply;

import java.util.List;

public interface ApplyService extends IService<Apply> {
    boolean saveBatchExtend(List<Apply> applyList);
}

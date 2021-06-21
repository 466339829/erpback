package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.GatherDetails;

import java.util.List;

public interface GatherDetailsService extends IService<GatherDetails> {
   boolean saveBatchExtend(List<GatherDetails> gatherDetails);
}

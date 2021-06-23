package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.mapper.GatherMapper;
import com.guigu.erp.pojo.Gather;
import com.guigu.erp.service.GathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GathServiceImpl extends ServiceImpl<GatherMapper, Gather> implements GathService {
    @Autowired
    GatherMapper gatherMapper;

    @Override
    public int updates(Gather gather) {
        Gather selectById = gatherMapper.selectById(gather.getId());
        selectById.setChecker(gather.getChecker());
        selectById.setCheckTime(gather.getCheckTime());
        selectById.setCheckTag(gather.getCheckTag());
        boolean b = this.updateById(selectById);
        return 0;
    }
}
    
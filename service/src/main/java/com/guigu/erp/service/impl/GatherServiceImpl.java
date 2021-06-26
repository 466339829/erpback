package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.mapper.GatherMapper;
import com.guigu.erp.pojo.Gather;
import com.guigu.erp.service.GatherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatherServiceImpl extends ServiceImpl<GatherMapper,Gather> implements GatherService {
    @Override
    public PageInfo<Gather> queryPage(int pageNo, int pageSize, Gather gather) {
        List<Gather> gatherList = null;
        if (gather != null) {
            //追加条件 入库单编号
            QueryWrapper<Gather> queryWrapper = new QueryWrapper<>();
            if (gather.getGatherId() != null && gather.getGatherId() != "")
                queryWrapper.eq("gather_id", gather.getGatherId());

            // 追加条件 登记时间开始
            if (gather.getRegisterTime() != null)
                queryWrapper.ge("register_time", gather.getRegisterTime());
            // 追加条件 登记时间结束
            if (gather.getRegisterTime2() != null )
                queryWrapper.le("register_time", gather.getRegisterTime2());

            if (gather.getGatherTag() != null && gather.getGatherTag() != "")
                queryWrapper.eq("gather_tag", gather.getGatherTag());
            PageHelper.startPage(pageNo, pageSize);
            gatherList = this.list(queryWrapper);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            gatherList = this.list();
        }
        return new PageInfo<Gather>(gatherList);
    }
}

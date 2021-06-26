package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ProceduringMapper;
import com.guigu.erp.pojo.Proceduring;
import com.guigu.erp.service.ProceduringService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProceduringServiceImpl extends ServiceImpl<ProceduringMapper, Proceduring> implements ProceduringService {
    @Override
    public List<Proceduring> selectByPid(int pid) {
        QueryWrapper<Proceduring> proceduringQueryWrapper = new QueryWrapper<>();
        proceduringQueryWrapper.eq("parent_id",pid);
        return this.list(proceduringQueryWrapper);
    }
}

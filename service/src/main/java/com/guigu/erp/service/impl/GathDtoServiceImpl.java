package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.GathDto;
import com.guigu.erp.mapper.GathDtoMapper;
import com.guigu.erp.service.GathDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GathDtoServiceImpl extends ServiceImpl<GathDtoMapper, GathDto> implements GathDtoService {
        @Autowired
        GathDtoMapper gathDtoMapper;
    @Override
    public List<GathDto> gathShenhe(int id) {


        List<GathDto> query = gathDtoMapper.query(id);


        return query;
    }
}

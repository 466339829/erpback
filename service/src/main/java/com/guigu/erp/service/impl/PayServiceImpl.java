package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.mapper.PayMapper;
import com.guigu.erp.pojo.Pay;
import com.guigu.erp.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements PayService {
    @Autowired
    private PayMapper payMapper;
    @Override
    public PageInfo<Pay> page(Integer pageNO, Integer pageSize) {
        PageHelper.startPage(pageNO,pageSize);
        List<Pay> page = payMapper.page();
        return new PageInfo<Pay>(page);
    }
}

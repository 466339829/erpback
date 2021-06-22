package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.PayDetailsMapper;
import com.guigu.erp.pojo.PayDetails;
import com.guigu.erp.service.PayDetailsService;
import org.springframework.stereotype.Service;

@Service
public class PayDetailsServiceImpl extends ServiceImpl<PayDetailsMapper, PayDetails> implements PayDetailsService {
}

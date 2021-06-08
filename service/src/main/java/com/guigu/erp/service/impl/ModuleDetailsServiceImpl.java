package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ModuleDetailsMapper;
import com.guigu.erp.pojo.ModuleDetails;
import com.guigu.erp.service.ModuleDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ModuleDetailsServiceImpl extends ServiceImpl<ModuleDetailsMapper, ModuleDetails> implements ModuleDetailsService {
}

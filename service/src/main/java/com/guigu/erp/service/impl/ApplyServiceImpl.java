package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.ApplyMapper;
import com.guigu.erp.pojo.Apply;
import com.guigu.erp.service.ApplyService;
import com.guigu.erp.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;
    @Override
    public boolean saveBatchExtend(List<Apply> applyList) {

        for (Apply apply:applyList){
            String longId = applyMapper.getLongId();
            String applyId = IDUtil.getApplyId(longId);
            apply.setApplyId(applyId);
        }
        return this.saveBatch(applyList);
    }
}

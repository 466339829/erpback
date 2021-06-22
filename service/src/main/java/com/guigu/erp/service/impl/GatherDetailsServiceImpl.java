package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.GatherDetailsMapper;
import com.guigu.erp.mapper.GatherMapper;
import com.guigu.erp.pojo.Gather;
import com.guigu.erp.pojo.GatherDetails;
import com.guigu.erp.service.GatherDetailsService;
import com.guigu.erp.util.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GatherDetailsServiceImpl extends ServiceImpl<GatherDetailsMapper, GatherDetails> implements GatherDetailsService {
    @Autowired
    private GatherMapper gatherMapper;

    @Transactional
    @Override
    public boolean saveBatchExtend(List<GatherDetails> gatherDetails) {

        GatherDetails details = gatherDetails.get(0);

        String longId = gatherMapper.LongId();
        String gatherId = IDUtil.getGatherId(longId);

        Gather gather = new Gather();
        gather.setGatherId(gatherId);
        gather.setStorer(details.getStorer());
        gather.setReason(details.getReason());
        gather.setAmountSum(details.getAmountSum());
        gather.setCostPriceSum(details.getCostPriceSum());
        gather.setGatheredAmountSum(details.getGatheredAmountSum());
        gather.setRemark(details.getRemark());
        gather.setRegister(details.getRegister());
        gather.setRegisterTime(details.getRegisterTime());
        gather.setChecker(details.getChecker());
        gather.setCheckTime(details.getCheckTime());
        gather.setCheckTag("0");
        gather.setStorer(details.getStorer());
        int insert = gatherMapper.insert(gather);
        for (GatherDetails men : gatherDetails) {
            men.setParentId(gather.getId());
            BigDecimal shul= men.getAmount().multiply(men.getCostPrice());
            men.setSubtotal(shul);
        }


        boolean result = this.saveBatch(gatherDetails);
        if (insert > 0 && result == true)
            return true;
        return false;
    }
}

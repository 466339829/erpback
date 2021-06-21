package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        String longId = applyMapper.getLongId();
        String applyId = IDUtil.getApplyId(longId);
        for (Apply apply:applyList){
            apply.setApplyId(applyId);
        }
        return this.saveBatch(applyList);
    }

    @Override
    public PageInfo<Apply> applyList(int pageNo,int pageSize,Apply apply) {
        PageHelper.startPage(pageNo, pageSize);
        List<Apply> list = applyMapper.applyList(apply);
        return new PageInfo<Apply>(list);
    }

    @Override
    public List<Apply> selectByAid(String applyId) {
        QueryWrapper<Apply> applyQueryWrapper = new QueryWrapper<>();
        applyQueryWrapper.eq("apply_id",applyId);
        return this.list(applyQueryWrapper);
    }
    //审核
    @Override
    public boolean checkTag(Apply apply) {
        QueryWrapper<Apply> applyQueryWrapper = new QueryWrapper<>();
        applyQueryWrapper.eq("apply_id",apply.getApplyId());
        List<Apply> list = this.list(applyQueryWrapper);
        for (Apply parem:list){
            parem.setChecker(apply.getChecker());
            parem.setCheckSuggestion(apply.getCheckSuggestion());
            parem.setCheckTime(apply.getCheckTime());
            parem.setCheckTag(apply.getCheckTag());
        }
        return this.updateBatchById(list);
    }

    @Override
    public PageInfo<Apply> queryPage(int pageNo, int pageSize, Apply apply) {
        List<Apply> applyList = null;
        QueryWrapper<Apply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("check_tag", 1);
        if (apply != null) {
            if (apply.getApplyId() != null && apply.getApplyId() != "")
                queryWrapper.eq("apply_id", apply.getApplyId());

            if (apply.getProductName() != null && apply.getProductName() != "")
                queryWrapper.like("product_name", apply.getProductName());

            if (apply.getCheckTime() != null )
                queryWrapper.ge("check_time", apply.getCheckTime());

            if (apply.getCheckTime2() != null )
                queryWrapper.le("check_time", apply.getCheckTime2());

            if (apply.getManufactureTag() != null && apply.getManufactureTag() != "")
                queryWrapper.eq("manufacture_tag", apply.getManufactureTag());

            PageHelper.startPage(pageNo, pageSize);
            applyList = this.list(queryWrapper);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            applyList = this.list(queryWrapper);
        }
        return new PageInfo<Apply>(applyList);
    }
}

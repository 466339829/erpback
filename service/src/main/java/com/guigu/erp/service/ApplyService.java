package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Apply;

import java.util.List;

public interface ApplyService extends IService<Apply> {
    boolean saveBatchExtend(List<Apply> applyList);
    PageInfo<Apply> applyList(int pageNo,int pageSize,Apply apply);

    List<Apply> selectByAid(String applyId);

    boolean checkTag(Apply apply);

    PageInfo<Apply> queryPage(int pageNo, int pageSize, Apply apply);
}

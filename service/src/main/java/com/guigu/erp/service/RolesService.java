package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Roles;
import com.guigu.erp.util.ResultUtil;


public interface RolesService extends IService<Roles> {
    PageInfo<Roles> queryPage(int pageNo, int pageSize, Roles roles);

    ResultUtil checkName(String name);

    ResultUtil insert(Roles userInfo);

    boolean deleteById(int id);
}

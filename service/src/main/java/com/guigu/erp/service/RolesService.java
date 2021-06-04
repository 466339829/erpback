package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Roles;
import com.guigu.erp.util.ResultUtil;

import java.util.List;


public interface RolesService extends IService<Roles> {
    PageInfo<Roles> queryPage(int pageNo, int pageSize, Roles roles);

    ResultUtil checkName(String name);

    ResultUtil insert(Roles userInfo);

    ResultUtil deleteById(int id);

    List<Roles> selectRoleByUid(int uid);

    List<Roles> selectAll(int uid, int userId);
}

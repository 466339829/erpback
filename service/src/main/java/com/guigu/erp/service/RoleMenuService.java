package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.RoleMenu;
import com.guigu.erp.util.ResultUtil;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenu> {
    ResultUtil saveOrUpdateBatchExtend(List<RoleMenu> roleMenus);

    ResultUtil deleteByRoleId(int roleId);
}

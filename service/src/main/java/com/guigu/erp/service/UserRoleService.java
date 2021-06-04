package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.UserRole;
import com.guigu.erp.util.ResultUtil;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    ResultUtil saveOrUpdateBatchExtend(List<UserRole> userRoles);

    ResultUtil deleteByRoleId(int userId);
}

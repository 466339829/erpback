package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Users;
import com.guigu.erp.util.ResultUtil;

public interface UsersService extends IService<Users> {
    ResultUtil login(String loginId, String password);

    PageInfo<Users> queryPage(int pageNo, int pageSize, Users user);

    ResultUtil checkName(String name);

    ResultUtil insert(Users userInfo);

    boolean deleteById(int id);
}

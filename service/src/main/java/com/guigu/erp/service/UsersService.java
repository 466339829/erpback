package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Users;
import com.guigu.erp.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UsersService extends IService<Users> {
    ResultUtil login(String loginId, String password, HttpServletRequest request);

    PageInfo<Users> queryPage(int pageNo, int pageSize, Users user);

    ResultUtil checkName(String name);

    ResultUtil insert(Users userInfo);

    ResultUtil deleteById(int id);
}

package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.guigu.erp.mapper.UserRoleMapper;
import com.guigu.erp.mapper.UsersMapper;
import com.guigu.erp.pojo.Menus;
import com.guigu.erp.pojo.UserRole;
import com.guigu.erp.pojo.Users;
import com.guigu.erp.service.UsersService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper,Users> implements UsersService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    //登录
    @Override
    public ResultUtil login(String loginId, String password) {
        ResultUtil<Users> resultUtil = new ResultUtil<>();
        QueryWrapper<Users> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("login_id", loginId);
        userInfoQueryWrapper.eq("password",password);
        userInfoQueryWrapper.eq("status",0);
        Users users = this.getOne(userInfoQueryWrapper);
        if (users != null) {
            resultUtil.setData(users);
            resultUtil.setMessage("登录成功");
            resultUtil.setResult(true);
        } else {
            resultUtil.setMessage("用户名或密码错误");
            resultUtil.setResult(false);
        }
        return resultUtil;
    }
    //条件分页查询
    @Override
    public PageInfo<Users> queryPage(int pageNo, int pageSize, Users user) {
        List<Users> users = null;
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        if (user != null) {
            if (user.getLoginId() != null && user.getLoginId() != "")
                queryWrapper.like("login_id",user.getLoginId());
            PageHelper.startPage(pageNo, pageSize);
            users = this.list(queryWrapper);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            users = this.list(queryWrapper);
        }
        return new PageInfo<Users>(users);
    }

    @Override
    public ResultUtil checkName(String name) {
        ResultUtil<Users> resultUtil = new ResultUtil<>();
        //判断用户名是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_id", name);
        Users user = this.getOne(queryWrapper);
        if (user != null) {
            resultUtil.setMessage("用户名已存在");
            resultUtil.setResult(false);
            return resultUtil;
        }
        resultUtil.setMessage("用户名可以使用");
        resultUtil.setResult(true);
        return resultUtil;
    }
    //新增
    @Override
    public ResultUtil insert(Users userInfo) {
        ResultUtil<Users> resultUtil = new ResultUtil<>();
        if (StringUtil.isEmpty(userInfo.getLoginId())&&StringUtil.isEmpty(userInfo.getPassword())) {
            resultUtil.setMessage("用户名或密码不能为空");
            resultUtil.setResult(false);
            return resultUtil;
        }
        //判断用户名是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_id", userInfo.getLoginId());
        Users user = this.getOne(queryWrapper);
        if (user != null) {
            resultUtil.setMessage("用户名已存在");
            resultUtil.setResult(false);
            return resultUtil;
        }
        userInfo.setCreationDate(new Date());
        userInfo.setStatus(0);
        boolean save = this.save(userInfo);
        if (save==true){
            resultUtil.setData(userInfo);
            resultUtil.setMessage("添加成功");
            resultUtil.setResult(true);
            return resultUtil;
        }else{
            resultUtil.setMessage("添加失败");
            resultUtil.setResult(false);
            return resultUtil;
        }
    }

    @Override
    public ResultUtil deleteById(int id) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",id);
        List<UserRole> roleList = userRoleMapper.selectList(userRoleQueryWrapper);
        if (roleList.size()>0){
            resultUtil.setResult(false);
            resultUtil.setMessage("已有角色正在使用,删除失败");
            return resultUtil;
        }
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("id",id);
        Users user = this.getOne(usersQueryWrapper);
        user.setStatus(1);
        boolean result = this.updateById(user);
        if (result){
        resultUtil.setResult(true);
        resultUtil.setMessage("删除成功");
        return resultUtil;
        }
        return resultUtil;
    }
}

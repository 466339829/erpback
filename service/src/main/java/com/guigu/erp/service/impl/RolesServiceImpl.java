package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.guigu.erp.mapper.RolesMapper;
import com.guigu.erp.pojo.Roles;
import com.guigu.erp.service.RolesService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements RolesService {
    //条件分页查询
    @Override
    public PageInfo<Roles> queryPage(int pageNo, int pageSize, Roles roles) {
        List<Roles> users = null;
        QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        if (roles != null) {
            if (roles.getName() != null && roles.getName() != "")
                queryWrapper.like("name",roles.getName());
            PageHelper.startPage(pageNo, pageSize);
            users = this.list(queryWrapper);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            users = this.list(queryWrapper);
        }
        return new PageInfo<Roles>(users);
    }

    //判断角色名是否存在
    @Override
    public ResultUtil checkName(String name) {
        ResultUtil<Roles> resultUtil = new ResultUtil<>();
        QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Roles user = this.getOne(queryWrapper);
        if (user != null) {
            resultUtil.setMessage("角色名已存在");
            resultUtil.setResult(false);
            return resultUtil;
        }
        resultUtil.setMessage("角色名可以使用");
        resultUtil.setResult(true);
        return resultUtil;
    }
    //新增
    @Override
    public ResultUtil insert(Roles roles) {
        ResultUtil<Roles> resultUtil = new ResultUtil<>();
        if (StringUtil.isEmpty(roles.getName())&& StringUtil.isEmpty(roles.getCode())&&StringUtil.isEmpty(roles.getDescn())) {
            resultUtil.setMessage("角色名称,角色编号或角色说明不能为空");
            resultUtil.setResult(false);
            return resultUtil;
        }
        //判断用户名是否存在
        QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", roles.getName());
        Roles user = this.getOne(queryWrapper);
        if (user != null) {
            resultUtil.setMessage("用户名已存在");
            resultUtil.setResult(false);
            return resultUtil;
        }

        roles.setStatus(0);
        boolean save = this.save(roles);
        if (save==true){
            resultUtil.setData(roles);
            resultUtil.setMessage("添加成功");
            resultUtil.setResult(true);
            return resultUtil;
        }else{
            resultUtil.setMessage("添加失败");
            resultUtil.setResult(false);
            return resultUtil;
        }
    }
    //删除（修改setStatus(1)）
    @Override
    public boolean deleteById(int id) {
        QueryWrapper<Roles> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("id",id);
        Roles roles = this.getOne(usersQueryWrapper);
        roles.setStatus(1);
        return this.updateById(roles);
    }
}

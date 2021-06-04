package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.guigu.erp.mapper.RoleMenuMapper;
import com.guigu.erp.mapper.RolesMapper;
import com.guigu.erp.mapper.UserRoleMapper;
import com.guigu.erp.pojo.RoleMenu;
import com.guigu.erp.pojo.Roles;
import com.guigu.erp.pojo.UserRole;
import com.guigu.erp.service.RolesService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements RolesService {
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
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
    public ResultUtil deleteById(int id) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("role_id",id);
        List<UserRole> roleList = userRoleMapper.selectList(userRoleQueryWrapper);
        if (roleList.size()>0){
            resultUtil.setResult(false);
            resultUtil.setMessage("已有用户正在使用角色,删除失败");
            return resultUtil;
        }
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuQueryWrapper);
        if (roleMenus.size()>0){
            resultUtil.setResult(false);
            resultUtil.setMessage("已有菜单正在使用角色,删除失败");
            return resultUtil;
        }
        QueryWrapper<Roles> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("id",id);
        Roles roles = this.getOne(usersQueryWrapper);
        roles.setStatus(1);
        boolean result = this.updateById(roles);
        if (result){
            resultUtil.setResult(true);
            resultUtil.setMessage("删除成功");
            return resultUtil;
        }
        return resultUtil;
    }

    @Override
    public List<Roles> selectRoleByUid(int uid) {
        return rolesMapper.selectRoleByUid(uid);
    }

    @Override
    public List<Roles> selectAll(int uid, int userId) {
        //当前点击的
        List<Roles> roles = rolesMapper.selectRoleByUid(userId);
        //所有的
        List<Roles> list = this.list();
        for (Roles role:list){
            for (Roles checkRole:roles){
                if (role.getId()==checkRole.getId()){
                    role.setChecked(true);
                }
            }
        }
        return list;
    }
}

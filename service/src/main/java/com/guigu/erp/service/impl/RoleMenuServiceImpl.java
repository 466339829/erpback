package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.RoleMenuMapper;
import com.guigu.erp.pojo.RoleMenu;
import com.guigu.erp.service.RoleMenuService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    //授权
    @Transactional
    @Override
    public ResultUtil saveOrUpdateBatchExtend(List<RoleMenu> roleMenus) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<RoleMenu>();
        queryWrapper.eq("role_id",roleMenus.get(0).getRole_id());
        List<RoleMenu> menuList = this.list(queryWrapper);
        int delete = 0;
        if (menuList.size()!=0) {
            //根据角色id 全删
            delete = roleMenuMapper.delete(queryWrapper);
        }
        //数据添加
        boolean result2 = this.saveBatch(roleMenus);
        if (result2==true&&menuList.size()==delete){
            resultUtil.setResult(true);
            resultUtil.setMessage("授权成功");
            return resultUtil;
        }
        resultUtil.setResult(false);
        resultUtil.setMessage("授权失败");
        return resultUtil;
    }

    // 删除所有权限
    @Override
    public ResultUtil deleteByRoleId(int roleId) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        boolean remove = this.remove(queryWrapper);
        if (remove){
            resultUtil.setResult(true);
            resultUtil.setMessage("删除成功");
            return resultUtil;
        }
        resultUtil.setResult(false);
        resultUtil.setMessage("删除失败");
        return resultUtil;
    }
}

package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.UserRoleMapper;
import com.guigu.erp.pojo.UserRole;
import com.guigu.erp.service.UserRoleService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Transactional
    @Override
    public ResultUtil saveOrUpdateBatchExtend(List<UserRole> userRoles) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>();
        queryWrapper.eq("user_id",userRoles.get(0).getUser_id());
        List<UserRole> roleList = this.list(queryWrapper);
        int delete = 0;
        if (roleList.size()!=0) {
            //根据角色id 全删
            delete = userRoleMapper.delete(queryWrapper);
        }
        //数据添加
        boolean result2 = this.saveBatch(userRoles);
        if (result2==true&&roleList.size()==delete){
            resultUtil.setResult(true);
            resultUtil.setMessage("授权成功");
            return resultUtil;
        }else {
            resultUtil.setResult(false);
            resultUtil.setMessage("授权失败");
            return resultUtil;
        }
    }

    @Override
    public ResultUtil deleteByRoleId(int userId) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        boolean remove = this.remove(queryWrapper);
        if (remove){
            resultUtil.setResult(true);
            resultUtil.setMessage("删除成功");
            return resultUtil;
        }else {
            resultUtil.setResult(false);
            resultUtil.setMessage("删除失败");
            return resultUtil;
        }
    }
}

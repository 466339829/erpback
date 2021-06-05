package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.guigu.erp.mapper.MenusMapper;
import com.guigu.erp.mapper.RoleMenuMapper;
import com.guigu.erp.pojo.Menus;
import com.guigu.erp.pojo.RoleMenu;
import com.guigu.erp.service.MenusService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenusServiceImpl extends ServiceImpl<MenusMapper, Menus> implements MenusService {
    @Autowired
    private MenusMapper menusMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    //左侧菜单
    @Override
    public List<Menus> selectMenusByUid(int uid) {
        //跟据用户查询所拥有的一级菜单
        List<Menus> list = menusMapper.selectMenuByParId(uid, 0);
        for (Menus parentMenu : list) {
            //跟据用户查询所拥有的二级菜单
            List<Menus> childMenuList = menusMapper.selectMenuByParId(uid, parentMenu.getId());
            parentMenu.setChildMenu(childMenuList);
            //跟据用户查询所拥有的三级菜单
            for (Menus menu : childMenuList) {
                List<Menus> menusList = menusMapper.selectMenuByParId(uid, menu.getId());
                menu.setChildMenu(menusList);
            }
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getChildMenu().size() == 0)
                list.remove(i);
        }

        return list;
    }

    //获取权限树 用户未拥有的权限disable为true
    @Override
    public List<Menus> selectAllMenu(int uid) {
        //跟据用户id查询所有三级菜单
        List<Menus> menuInfos = menusMapper.allMenuByUid(uid);
        //查询所有菜单
        List<Menus> list = menusMapper.selectMenuChild(0);

        for (Menus menus : list) {
            //跟据菜单id查询所有二级菜单
            List<Menus> childList = menusMapper.selectMenuChild(menus.getId());
            menus.setChildMenu(childList);
            for (Menus childMenuList : childList) {
                //跟据菜单id查询所有三级菜单
                List<Menus> menusList = menusMapper.selectMenuChild(childMenuList.getId());
                //循环用户拥有的菜单
                for (Menus childItem : menusList) {
                    //设置所有权限不能选中
                    childItem.setDisable(true);
                    //循环所有二级菜单
                    for (Menus menu : menuInfos) {
                        //设置用户拥有的权限选中
                        if (childItem.getId() == menu.getId()) {
                            childItem.setDisable(false);
                            continue;
                        }
                    }
                }
                childMenuList.setChildMenu(menusList);
            }
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getChildMenu().size() == 0)
                list.remove(i);
        }
        return list;
    }
    //跟据rid获取已拥有的三级权限
    @Override
    public List<Menus> selectMenuByRid(int rid) {
        return  menusMapper.selectMenuByRid(rid);
    }
    //分页
    @Override
    public PageInfo<Menus> queryPage(int pageNo, int pageSize, Menus menus) {
        List<Menus> menusList = null;
        QueryWrapper<Menus> queryMenu = new QueryWrapper<>();
        queryMenu.eq("status", 0);
        if (menus != null) {
            if (menus.getName() != null && menus.getName() != "")
                queryMenu.like("name",menus.getName());
            if (menus.getSeq() != null )
                queryMenu.eq("seq",menus.getSeq());
            PageHelper.startPage(pageNo, pageSize);
            menusList = this.list(queryMenu);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            menusList = this.list(queryMenu);
        }

        QueryWrapper<Menus> menuQueryWrapper = new QueryWrapper<Menus>();
        menuQueryWrapper.eq("parent_id", 0);
        List<Menus> parentMenu = this.list(menuQueryWrapper);
        for (Menus parent:parentMenu){
            for (Menus childMenu : menusList) {
                //判断是否为二级菜单
                if (childMenu.getParentId()==parent.getId())
                    //设置二级菜单的一级菜单
                    childMenu.setParentName(parent.getName());
                    //设置一级菜单
                else if (childMenu.getParentId()==0)
                    childMenu.setParentName("无");
            }
        }

        QueryWrapper<Menus> query = new QueryWrapper<Menus>();
        query.eq("seq", 2);
        List<Menus> list = this.list(query);
        for (Menus m:list){
            for (Menus childMenu : menusList) {
                //判断是否为三级菜单
                if (childMenu.getParentId()==m.getId())
                    //设置三级级菜单的二级菜单
                    childMenu.setParentName(m.getName());
                    //设置一级菜单
                else if (childMenu.getParentId()==0)
                    childMenu.setParentName("无");
            }
        }

        return new PageInfo<Menus>(menusList);
    }

    ////判断菜单名是否存在
    @Override
    public ResultUtil checkName(String name) {
        ResultUtil<Menus> resultUtil = new ResultUtil<>();
        QueryWrapper<Menus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Menus menus = this.getOne(queryWrapper);
        if (menus != null) {
            resultUtil.setMessage("菜单名已存在");
            resultUtil.setResult(false);
            return resultUtil;
        }
        resultUtil.setMessage("菜单名可以使用");
        resultUtil.setResult(true);
        return resultUtil;
    }
    //新增
    @Override
    public ResultUtil insert(Menus menus) {
        ResultUtil<Menus> resultUtil = new ResultUtil<>();
        if (StringUtil.isEmpty(menus.getName())&& StringUtil.isEmpty(menus.getName())) {
            resultUtil.setMessage("用户名或密码不能为空");
            resultUtil.setResult(false);
            return resultUtil;
        }
        //判断用户名是否存在
        QueryWrapper<Menus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", menus.getName());
        Menus menu = this.getOne(queryWrapper);
        if (menu != null) {
            resultUtil.setMessage("用户名已存在");
            resultUtil.setResult(false);
            return resultUtil;
        }
        menus.setStatus(0);
        boolean save = this.save(menus);
        if (save==true){
            resultUtil.setData(menus);
            resultUtil.setMessage("添加成功");
            resultUtil.setResult(true);
            return resultUtil;
        }else{
            resultUtil.setMessage("添加失败");
            resultUtil.setResult(false);
            return resultUtil;
        }
    }

    //查询父级菜单
    @Override
    public List<Menus> selectByMenuByParentId(int seq) {
        return menusMapper.selectByMenuByParentId(seq);
    }

    @Override
    public ResultUtil deleteById(int id) {
        ResultUtil<Menus> resultUtil = new ResultUtil<>();
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("menu_id",id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuQueryWrapper);
        if (roleMenus.size()>0){
            resultUtil.setMessage("已有角色正在使用,删除失败");
            resultUtil.setResult(false);
            return resultUtil;
        }
        Menus menus = this.getById(id);
        menus.setStatus(1);
        boolean resule = this.updateById(menus);
        if (resule) {
            resultUtil.setMessage("删除成功");
            resultUtil.setResult(true);
            return resultUtil;
        }else {
            resultUtil.setMessage("删除失败");
            resultUtil.setResult(false);
            return resultUtil;
        }
    }

}

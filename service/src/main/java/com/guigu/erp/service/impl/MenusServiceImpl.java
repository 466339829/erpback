package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.MenusMapper;
import com.guigu.erp.pojo.Menus;
import com.guigu.erp.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenusServiceImpl extends ServiceImpl<MenusMapper, Menus> implements MenusService {
    @Autowired
    private MenusMapper menusMapper;

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
}

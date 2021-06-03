package com.guigu.erp.controller;

import com.guigu.erp.pojo.Menus;
import com.guigu.erp.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenusController {
    @Autowired
    private MenusService menusService;
    /**
     * 根据用户查询左侧菜单
     * @param
     * @return
     */
    @RequestMapping("/selectMenusByUid/{uid}")
    public List<Menus> queryMenuByUid(@PathVariable int uid){
        return  menusService.selectMenusByUid(uid);
    }
    /**
     * 获取权限树
     * @param uid
     * @return
     */
    @RequestMapping("/selectAllMenu/{uid}")
    public List<Menus> selectAllMenu(@PathVariable int uid){
        return menusService.selectAllMenu(uid);
    }

    /**
     * 跟据rid获取已拥有的三级权限
     * @param rid
     * @return
     */
    @RequestMapping("/selectMenuByRid/{rid}")
    public List<Menus> queryMenuByRid(@PathVariable int rid){
        return menusService.selectMenuByRid(rid);
    }
}

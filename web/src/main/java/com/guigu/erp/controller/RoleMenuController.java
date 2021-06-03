package com.guigu.erp.controller;

import com.guigu.erp.pojo.RoleMenu;
import com.guigu.erp.service.RoleMenuService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 授权
     * @param roleMenus
     * @return
     */
    @RequestMapping(value = "/setRoleMenu",produces = "application/json;charset=utf-8")
    public ResultUtil setRoleMenu(@RequestBody List<RoleMenu> roleMenus){
        return roleMenuService.saveOrUpdateBatchExtend(roleMenus);
    }

    /**
     * 删除所有权限
     * @param roleId
     * @return
     */
    @RequestMapping("/deleteByRoleId/{roleId}")
    public ResultUtil deleteByRoleId(@PathVariable int roleId ){
        return roleMenuService.deleteByRoleId(roleId);
    }
}

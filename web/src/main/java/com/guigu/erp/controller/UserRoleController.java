package com.guigu.erp.controller;

import com.guigu.erp.pojo.UserRole;
import com.guigu.erp.service.UserRoleService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userRole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    /**
     * 授权
     * @param userRoles
     * @return
     */
    @RequestMapping(value = "/setUserRole",produces = "application/json;charset=utf-8")
    public ResultUtil setRoleMenu(@RequestBody List<UserRole> userRoles){
        return userRoleService.saveOrUpdateBatchExtend(userRoles);
    }
    /**
     * 删除所有权限
     * @param userId
     * @return
     */
    @RequestMapping("/deleteByUserId/{userId}")
    public ResultUtil deleteByRoleId(@PathVariable int userId ){
        return userRoleService.deleteByRoleId(userId);
    }
}

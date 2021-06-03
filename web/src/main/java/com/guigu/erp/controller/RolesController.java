package com.guigu.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.erp.pojo.Roles;
import com.guigu.erp.service.RolesService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;
    /**
     * 条件分页查询角色
     * @param pageNo
     * @param pageSize
     * @return
     */

    @RequestMapping("/page")
    public IPage<Roles> page(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                             @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                             Roles roles){
        //组装查询条件
        QueryWrapper<Roles> queryWrapper =new QueryWrapper<Roles>();
        queryWrapper.eq("status",0);
        if(!StringUtils.isEmpty(roles.getName())){
            queryWrapper.like("name",roles.getName());
        }
        return  rolesService.page(new Page<Roles>(pageNo,pageSize),queryWrapper);
    }

    /**
     * 查询用户名是否存在可用
     * @param name
     * @return
     */
    @RequestMapping(value = "/checkName", produces = "application/json;charset=utf-8")
    public ResultUtil checkName(String name) {
        return rolesService.checkName(name);
    }

    /**
     * 新增
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/insert", produces = "application/json;charset=utf-8")
    public ResultUtil insert(Roles userInfo) {
        return rolesService.insert(userInfo);
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable int id){
        return  rolesService.deleteById(id);
    }
    /**
     * 单个查询
     * @param id
     * @return
     */
    @RequestMapping("/selectById/{id}")
    public Roles selectById(@PathVariable int id){
        return  rolesService.getById(id);
    }
    /**
     * 新增
     *
     * @param roles
     * @return
     */
    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    public boolean update(Roles roles) {
        return rolesService.updateById(roles);
    }
}

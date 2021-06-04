package com.guigu.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Menus;
import com.guigu.erp.service.MenusService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    /**
     * 条件分页查询
     * @param pageNo
     * @param pageSize
     * @param menus
     * @return
     */
    @RequestMapping(value = "/page", produces = "application/json;charset=utf-8")
    public PageInfo<Menus> page(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                Menus menus) {
        return menusService.queryPage(pageNo, pageSize, menus);
    }

    /**
     * 查询菜单名是否存在可用
     * @param name
     * @return
     */
    @RequestMapping(value = "/checkName", produces = "application/json;charset=utf-8")
    public ResultUtil checkName(String name) {
        return menusService.checkName(name);
    }
    /**
     * 新增
     *
     * @param menus
     * @return
     */
    @RequestMapping(value = "/insert", produces = "application/json;charset=utf-8")
    public ResultUtil insert(Menus menus) {
        return menusService.insert(menus);
    }

    @RequestMapping("/selectByMenuByParentId/{seq}")
    public List<Menus> selectByMenuByParentId(@PathVariable int seq){
        return menusService.selectByMenuByParentId(seq);
    }

    /**
     * 单个查询
     * @param id
     * @return
     */
    @RequestMapping("/selectById/{id}")
    public Menus selectById(@PathVariable int id){
        return  menusService.getById(id);
    }

    @RequestMapping("/selectParMenu/{seq}")
    public List<Menus> selectParMenu(@PathVariable int seq){
        QueryWrapper<Menus> menusQueryWrapper = new QueryWrapper<>();
        menusQueryWrapper.eq("seq",seq);
       return menusService.list(menusQueryWrapper);
    }
    /**
     * 修改
     *
     * @param menus
     * @return
     */
    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    public boolean update(Menus menus) {
        return menusService.updateById(menus);
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ResultUtil delete(@PathVariable int id){
        return  menusService.deleteById(id);
    }
}

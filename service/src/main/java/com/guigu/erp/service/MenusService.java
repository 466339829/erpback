package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Menus;
import com.guigu.erp.util.ResultUtil;

import java.util.List;

public interface MenusService extends IService<Menus> {
    List<Menus> selectMenusByUid(int uid);

    List<Menus> selectAllMenu(int uid);

    List<Menus> selectMenuByRid(int rid);

    PageInfo<Menus> queryPage(int pageNo, int pageSize, Menus menus);

    ResultUtil checkName(String name);

    ResultUtil insert(Menus menus);

    List<Menus> selectByMenuByParentId(int seq);

    ResultUtil deleteById(int id);
}

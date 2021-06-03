package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.Menus;

import java.util.List;

public interface MenusService extends IService<Menus> {
    List<Menus> selectMenusByUid(int uid);

    List<Menus> selectAllMenu(int uid);

    List<Menus> selectMenuByRid(int rid);
}

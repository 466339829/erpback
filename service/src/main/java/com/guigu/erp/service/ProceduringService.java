package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.Proceduring;

import java.util.List;

public interface ProceduringService extends IService<Proceduring> {

    List<Proceduring> selectByPid(int pid);
}

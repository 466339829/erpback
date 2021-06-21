package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.File;
import com.guigu.erp.util.ResultUtil;

import java.util.Date;

public interface FileService extends IService<File> {
    boolean insert(File file);

    boolean checkTag(File file);

    boolean updateFile(File file);

    ResultUtil deleteById(int id);

    ResultUtil foreverDelete(int id);

    ResultUtil recoveryById(int id);

    ResultUtil checkName(String name);

    PageInfo queryPage(int pageNo, int pageSize, File file);

    boolean selectByProductId(String productId);
}

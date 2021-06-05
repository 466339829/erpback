package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.File;
import com.guigu.erp.util.ResultUtil;

public interface FileService extends IService<File> {
    boolean insert(File file);

    boolean checkTag(int id);

    boolean updateFile(File file);

    ResultUtil deleteById(int id);

    ResultUtil foreverDelete(int id);

    ResultUtil recoveryById(int id);
}

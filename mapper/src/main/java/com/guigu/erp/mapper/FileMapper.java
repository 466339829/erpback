package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.File;
import org.apache.ibatis.annotations.Select;

public interface FileMapper extends BaseMapper<File> {
    @Select("select f.`product_id` from `d_file` f \n" +
            "order by  f.`id` desc limit 0,1")
    String getLongId();
}

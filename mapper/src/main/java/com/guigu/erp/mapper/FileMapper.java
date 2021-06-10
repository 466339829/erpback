package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.File;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface FileMapper extends BaseMapper<File> {
    @Select("select f.`product_id` from `d_file` f \n" +
            "order by  f.`id` desc limit 0,1")
    String getLongId();

    @Update("UPDATE d_file set design_procedure_tag=1 WHERE id=#{id}")
    int filsupdate(int id);

    @Update("UPDATE d_file set design_procedure_tag=0 WHERE id=#{id}")
    int fisupda(int id);
}

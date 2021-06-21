package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Manufacture;
import org.apache.ibatis.annotations.Select;

public interface ManufactureMapper extends BaseMapper<Manufacture> {
    @Select("select m.`manufacture_id` from `m_manufacture` m order by m.`id` desc limit 0,1")
    String getLongId();
}

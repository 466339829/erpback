package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Gather;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GatherMapper extends BaseMapper<Gather> {
    @Select("select m.`gather_id` from `s_gather` m order by m.`id` desc limit 0,1")
    String LongId();


}

package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Module;
import org.apache.ibatis.annotations.Select;

public interface ModuleMapper extends BaseMapper<Module> {
    @Select("select m.`design_id` from `d_module` m order by m.`id` desc limit 0,1")
    String getLongId();
}

package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Apply;
import org.apache.ibatis.annotations.Select;

public interface ApplyMapper extends BaseMapper<Apply> {
    @Select("select m.`apply_id` from `m_apply` m order by m.`id` desc limit 0,1")
    String getLongId();
}

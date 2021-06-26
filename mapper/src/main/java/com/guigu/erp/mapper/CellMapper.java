package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Cell;
import org.apache.ibatis.annotations.Select;

public interface CellMapper extends BaseMapper<Cell> {

    @Select("select m.`store_id` from `s_cell` m order by m.`id` desc limit 0,1")
    String LongId();
}

package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Cell;
import org.apache.ibatis.annotations.Select;

public interface CellMapper extends BaseMapper<Cell> {
    @Select("SELECT COUNT(*) FROM s_cell WHERE product_id=#{befroeId}")
    Integer selectByBeforeId(String  befroeId);
}

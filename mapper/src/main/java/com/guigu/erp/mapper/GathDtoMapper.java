package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.GathDto;
import com.guigu.erp.pojo.Gather;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GathDtoMapper extends BaseMapper<GathDto> {

    @Select("SELECT * FROM s_gather ga\n" +
            "            LEFT JOIN s_gather_details de\n" +
            "            ON ga.id=de.parent_id\n" +
            "            WHERE de.parent_id=#{id}")
    List<GathDto> query( int id);
}

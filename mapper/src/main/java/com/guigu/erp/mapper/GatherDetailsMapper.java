package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.GatherDetails;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GatherDetailsMapper  extends BaseMapper<GatherDetails> {
    @Select("SELECT * FROM s_gather_details WHERE parent_id=#{gid}")
    List<GatherDetails> selectByPId(Integer gid);
}

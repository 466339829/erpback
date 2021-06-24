package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Pay;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PayMapper extends BaseMapper<Pay> {
    @Select("SELECT * FROM s_pay WHERE check_tag=0")
    List<Pay> page();
}

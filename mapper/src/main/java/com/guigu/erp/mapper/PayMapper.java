package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Pay;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PayMapper extends BaseMapper<Pay> {
    @Select("SELECT * FROM s_pay WHERE pay_tag=1")
    List<Pay> page();
    @Select("select m.`manufacture_id` from `m_manufacture` m order by m.`id` desc limit 0,1")
    String getLongId();
}

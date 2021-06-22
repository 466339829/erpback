package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Pay;
import org.apache.ibatis.annotations.Select;

public interface PayMapper extends BaseMapper<Pay> {
    @Select("select f.`pay_id` from `s_pay` f \n" +
            "order by  f.`id` desc limit 0,1")
    String getLongId();
}

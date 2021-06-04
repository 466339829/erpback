package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Roles;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RolesMapper extends BaseMapper<Roles> {
    @Select("select r.`id`,r.`name` from `sys_roles` r left join `sys_user_role` ur\n" +
            "on r.`id` = ur.`role_id` where ur.`user_id` =#{uid}")
    List<Roles> selectRoleByUid(int uid);
}

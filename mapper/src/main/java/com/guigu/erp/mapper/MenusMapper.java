package com.guigu.erp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.erp.pojo.Menus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenusMapper extends BaseMapper<Menus> {
    //跟据用户查询所拥有的一级，二级，三级 菜单
    @Select("select distinct m.id,m.`parent_id`,m.`name`,m.`image_url`,m.`link_url` from `sys_menus` m left join `sys_menu_role` mr \n" +
            "on m.`id` = mr.`menu_id` left join `sys_user_role` ur \n" +
            "on mr.`role_id` = ur.`role_id` where ur.`user_id` =#{uid} and m.`parent_id`=#{parentId}")
    List<Menus> selectMenuByParId(@Param("uid") int uid, @Param("parentId") int parentId);


    //跟据用户id查询所有三级菜单
    @Select("select distinct m.id,m.`parent_id`,m.`name`,m.`image_url`,m.`link_url` from `sys_menus` m left join `sys_menu_role` mr \n" +
            "on m.`id` = mr.`menu_id` left join `sys_user_role` ur \n" +
            "on mr.`role_id` = ur.`role_id` where ur.`user_id` =#{uid} and m.`seq`=3")
    List<Menus> allMenuByUid(int uid);

    //跟据id查询子菜单 where（parent_id =id）
    @Select("select distinct m.id,m.`parent_id`,m.`name`,m.`image_url`,m.`link_url`\n" +
            "from sys_menus m where m.`parent_id`=#{id}")
    List<Menus> selectMenuChild(Integer id);

    //跟据rid获取已拥有的三级权限
    @Select("select m.id,m.`parent_id`,m.`name`,m.`image_url`,m.`link_url` from `sys_menus` m left join `sys_menu_role` mr \n" +
            "on m.`id` = mr.`menu_id`  where mr.`role_id` =#{rid} and m.`seq`=3")
    List<Menus> selectMenuByRid(int rid);

    @Select("select m.`id`,m.`name` from `sys_menus` m where m.`seq` =#{seq}")
    List<Menus> selectByMenuByParentId(int seq);
}

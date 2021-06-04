package com.guigu.erp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_menus")
public class Menus {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("parent_id")
    private Integer parentId;
    private Integer seq;
    private String name;
    @TableField("image_url")
    private String imageUrl;
    @TableField("link_url")
    private String linkUrl;
    private Integer status;
    @TableField(value = "parentName",exist = false)
    private String parentName;
    @TableField(exist = false)
    List<Menus> childMenu;
    @TableField(exist = false)
    private boolean disable;
}

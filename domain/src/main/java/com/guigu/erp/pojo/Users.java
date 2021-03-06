package com.guigu.erp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("sys_users")
public class Users {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("login_id")
    private String loginId;
    @TableField("password")
    private String password;
    @TableField("photo_path")
    private String photo;
    private Integer status;
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("creationDate")
    private Date creationDate;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(exist = false)
    private Date creationDate2;
    @TableField(exist = false)
    private String roleName;
}

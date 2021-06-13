package com.guigu.erp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("d_config_file_kind")
public class ConfigFileKind {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer pId;

    private String kindId;

    private String kindName;

    private Integer kindLevel;

}

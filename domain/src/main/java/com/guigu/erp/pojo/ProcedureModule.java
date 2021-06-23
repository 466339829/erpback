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
@TableName("m_procedure_module")
public class ProcedureModule {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private Integer detailsNumber;

    private String productId;

    private String productName;

    private Float costPrice;

    private Float amount;

    private Float renewAmount;

    private Float realAmount;

    private Float subtotal;

    private Float realSubtotal;

    @TableField(value = "shuliang" ,exist = false)
    private Integer shuliang;

    @TableField(value = "register" ,exist = false)
    private  String register;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(exist = false)
    private Date registerTime;

    @TableField(value = "procedureResponsiblePerson" ,exist = false)
    private String procedureResponsiblePerson;

    @TableField(value = "labourHourAmount" ,exist = false)
    private Integer labourHourAmount;

}

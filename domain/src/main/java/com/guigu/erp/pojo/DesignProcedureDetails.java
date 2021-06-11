package com.guigu.erp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("m_design_procedure_details")
public class DesignProcedureDetails {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private Integer detailsNumber;

    private String procedureId;

    private String procedureName;

    private BigDecimal labourHourAmount;

    private String procedureDescribe;

    private String amountUnit;

    private BigDecimal costPrice;

    private BigDecimal subtotal;

    private BigDecimal moduleSubtotal;

    private String register;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date registerTime;

    private String designModuleTag;

    private String designModuleChangeTag;
    @TableField(exist = false)
    List<ModuleDetails> moduleDetails;

}

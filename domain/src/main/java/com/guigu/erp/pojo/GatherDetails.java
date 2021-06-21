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

@Data
@TableName("s_gather_details")
public class GatherDetails {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private String productId;

    private String productName;

    private String productDescribe;

    private BigDecimal amount;

    private String amountUnit;

    private BigDecimal costPrice;

    private BigDecimal subtotal;

    private BigDecimal gatheredAmount;

    private String gatherTag;


    @TableField(value = "gatherId", exist = false)
    private String gatherId;


    @TableField(value = "storer", exist = false)
    private String storer;

    @TableField(value = "reason", exist = false)
    private String reason;

    @TableField(value = "reasonexact", exist = false)
    private String reasonexact;

    @TableField(value = "amountSum", exist = false)
    private BigDecimal amountSum;

    @TableField(value = "costPriceSum", exist = false)
    private BigDecimal costPriceSum;

    @TableField(value = "gatheredAmountSum", exist = false)
    private BigDecimal gatheredAmountSum;

    @TableField(value = "remark", exist = false)
    private String remark;

    @TableField(value = "register", exist = false)
    private String register;

    @TableField(value = "registerTime", exist = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerTime;

    @TableField(value = "checkTag", exist = false)
    private String checkTag;
    @TableField(value = "checker", exist = false)
    private String checker;
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "checkTime", exist = false)
    private Date checkTime;
}

package com.guigu.erp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("s_pay_details")
public class PayDetails {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("parent_id")
    private Integer parentId;

    private String productId;

    private String productName;

    private String productDescribe;

    private Float amount;

    private String amountUnit;

    private Float costPrice;

    private Float subtotal;

    private Float paidAmount;

    private String payTag;

}

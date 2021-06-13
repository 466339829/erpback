package com.guigu.erp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("d_module_details")
public class ModuleDetails {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private Integer detailsNumber;

    private String productId;

    private String productName;

    private String type;

    private String productDescribe;

    private String amountUnit;

    private Integer amount;

    private Integer residualAmount;

    private BigDecimal costPrice;

    private BigDecimal subtotal;

    @TableField(value = "goodsId" ,exist = false)
    private String goodsId;
    @TableField(value = "designer" ,exist = false)
    private String designer;
    @TableField(value = "moduleDescribe" ,exist = false)
    private String moduleDescribe;
    @TableField(value = "register" ,exist = false)
    private String register;
    @TableField(value = "changer" ,exist = false)
    private String changer;
    @TableField(value = "designId" ,exist = false)
    private String designId;

}

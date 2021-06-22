package com.guigu.erp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("m_procedure")
public class Procedure {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private Integer detailsNumber;

    private String procedureId;

    private String procedureName;

    private Float labourHourAmount;

    private Float realLabourHourAmount;

    private Float subtotal;

    private Float realSubtotal;

    private Float moduleSubtotal;

    private Float realModuleSubtotal;

    private Float costPrice;

    private Float demandAmount;

    private Float realAmount;

    private String procedureFinishTag;

    private String procedureTransferTag;


}

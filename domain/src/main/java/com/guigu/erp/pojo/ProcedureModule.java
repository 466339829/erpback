package com.guigu.erp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


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


}

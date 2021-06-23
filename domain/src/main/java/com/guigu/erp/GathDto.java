package com.guigu.erp;

import com.guigu.erp.pojo.Gather;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GathDto extends Gather {
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
}

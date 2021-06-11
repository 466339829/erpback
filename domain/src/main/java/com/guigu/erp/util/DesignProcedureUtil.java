package com.guigu.erp.util;

import com.guigu.erp.pojo.DesignProcedure;
import com.guigu.erp.pojo.DesignProcedureDetails;
import lombok.Data;

import java.util.List;

@Data
public class DesignProcedureUtil {
    private DesignProcedure designProcedure;
    private List<DesignProcedureDetails> designProcedureDetails;
}

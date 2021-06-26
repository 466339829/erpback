package com.guigu.erp.util;

import com.guigu.erp.pojo.ProcedureModule;
import com.guigu.erp.pojo.Proceduring;
import lombok.Data;

import java.util.List;

@Data
public class ProcedureModuleUtil {
    private Proceduring proceduring;

    List<ProcedureModule> list;
}

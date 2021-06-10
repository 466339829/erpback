package com.guigu.erp.util;

import com.guigu.erp.pojo.Module;
import com.guigu.erp.pojo.ModuleDetails;
import lombok.Data;

import java.util.List;

@Data
public class ListUtil {
    private Module module;
    private List<ModuleDetails> moduleDetailsList;
}

package com.guigu.erp.util;

import com.guigu.erp.pojo.File;

import java.util.concurrent.atomic.AtomicLong;

public class IDUtil {
    //getProductId
    public static String getProductId(String longId, File file){
        String substring ="";
        if (longId != null) {
            substring = longId.substring(longId.length()-6);
            AtomicLong at = new AtomicLong(Long.parseLong(substring));
            Long atLong = at.incrementAndGet();
            return "100" + file.getFirstKindId() + file.getSecondKindId() + file.getThirdKindId()+ String.format("%06d", atLong);
         } else {
            return "100" + file.getFirstKindId() + file.getSecondKindId() + file.getThirdKindId() + "000001";
        }
    }
}

package com.guigu.test;

import com.guigu.erp.util.IDUtil;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

public class test1 {
    @Test
    public void test(){
        String id = "100010101000019";
        String substring =id.substring(id.length()-6);
        System.out.println(substring);
        AtomicLong at = new AtomicLong(Long.parseLong(substring));
        Long atLong = at.incrementAndGet();
        System.out.println(atLong);
        String str = String.format("%06d", atLong);
        System.out.println(str);
    }
}

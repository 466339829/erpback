package com.guigu.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Cell;
import com.guigu.erp.pojo.Pay;
import com.guigu.erp.pojo.PayDetails;
import com.guigu.erp.service.CellService;
import com.guigu.erp.service.PayDetailsService;
import com.guigu.erp.service.PayService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private PayService payService;
    @Autowired
    private PayDetailsService payDetailsService;
    @Autowired
    private CellService cellService;
    //出库调度
    @RequestMapping("/selectList")
    public PageInfo<Pay> selectList(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize){
        return payService.page(pageNo,pageSize);
    }
    //申请调度
    @RequestMapping("/selectId/{id}")
    public List<PayDetails> selectId(@PathVariable Integer id){
        QueryWrapper<PayDetails> queryWrapper=new QueryWrapper<PayDetails>();
        queryWrapper.eq("parent_id",id);
        return payDetailsService.list(queryWrapper);
    }
    //最终调度
    @Transactional
    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8")
    public boolean update(PayDetails payDetails){

        String pid=payDetails.getProductId();
        QueryWrapper<Cell> queryWrapper=new QueryWrapper<Cell>();
        queryWrapper.eq("product_id",pid);
        Cell one = cellService.getOne(queryWrapper);
        if (one==null) return  false;
        if (one !=null ){
            if (!"2".equals(one.getCheckTag())&& payDetails.getPaidAmount().floatValue()>one.getAmount().floatValue()){
                return false;
            }
        }
        one.setAmount(BigDecimal.valueOf(one.getAmount().floatValue()-payDetails.getPaidAmount().floatValue()));
        boolean result = cellService.updateById(one);
        payDetails.setPayTag("2");
        UpdateWrapper<PayDetails> updateWrapper=new UpdateWrapper<PayDetails>();
        updateWrapper.eq("id",payDetails.getId());
        return payDetailsService.update(payDetails,updateWrapper);
    }
    //通过提交
    @RequestMapping("/payupdate")
    public boolean payupdate(Pay pay){
        UpdateWrapper<Pay> updateWrapper=new UpdateWrapper<Pay>();
        updateWrapper.eq("id",pay.getId());
        pay.setPayTag(pay.getPayTag());
        return payService.update(pay,updateWrapper);
    }
    //不通过提交
    @RequestMapping("/payupdate2")
    public boolean payupdate2(Pay pay){
        UpdateWrapper<Pay> updateWrapper=new UpdateWrapper<Pay>();
        updateWrapper.eq("id",pay.getId());
        pay.setCheckTag("2");
        return payService.update(pay,updateWrapper);
    }
}

package com.guigu.erp.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.guigu.erp.pojo.Cell;
import com.guigu.erp.pojo.File;
import com.guigu.erp.service.CellService;
import com.guigu.erp.service.FileService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Cells")
public class CellController {
    @Autowired
    CellService cellService;
    @Autowired
    FileService fileService;

    @RequestMapping("/cesint")
    public boolean cesint(Cell cell, int id) {
        System.out.println(id);
        boolean inserts = cellService.inserts(cell, id);
        return inserts ? true : false;

    }

    @RequestMapping("/fuHe")
    @ResponseBody
    public PageInfo<Cell> fuHe(@RequestParam(defaultValue = "1") int pageNo,
                               @RequestParam(defaultValue = "5") int pageSize,
                               Cell cell) {
        PageInfo<Cell> seles = cellService.seles(pageNo, pageSize, cell);
        return seles;
    }

    @RequestMapping("/fuHebyid")
    public Cell fuHebyid(int id) {
        System.out.println(id);
        return cellService.getById(id);
    }

    //取消复核修改后删除
    @RequestMapping("/fudeleteid")
    public int fudeleteid(int id, String productId) {
        return cellService.deleteas(id, productId);
    }

    //成功复核提交
    @RequestMapping("/updateio")
    public Boolean fudeinset(Cell cell) {
        return cellService.updateById(cell);
    }

    @RequestMapping("/Cellquery")
    @ResponseBody
    public IPage<Cell> Cellquery(@RequestParam(defaultValue = "1") int pageNo,
                                 @RequestParam(defaultValue = "5") int pageSize) {


        return cellService.page(new Page<Cell>(pageNo, pageSize));
    }

    @RequestMapping("/selectSafety2")
    public IPage<Cell> updateSafety(@RequestParam(defaultValue = "1") int pageNo,
                                    @RequestParam(defaultValue = "5") int pageSize,
                                    Cell cell) {
        QueryWrapper<Cell> wrapper = new QueryWrapper<Cell>();
        wrapper.eq("check_tag", 2);
        if (!StringUtil.isEmpty(cell.getProductName())) {

            wrapper.like("product_name", cell.getProductName());
        } else if (!StringUtil.isEmpty(cell.getFirstKindName())) {
            wrapper.like("first_kind_name", cell.getFirstKindName());
        } else if (!StringUtil.isEmpty(cell.getSecondKindName())) {
            wrapper.like("second_kind_name", cell.getSecondKindName());
        } else if (!StringUtil.isEmpty(cell.getThirdKindName())) {
            wrapper.like("third_kind_name", cell.getThirdKindName());
        }

        return cellService.page(new Page<Cell>(pageNo, pageSize), wrapper);
    }
    @RequestMapping("/StaByid")
    public Cell StaByid(int id){
     return cellService.getById(id);
    }
    @RequestMapping("/updateStatyquery")
    @ResponseBody
    public Boolean updateStatyquery(Cell cell){
        System.out.println(111);
        cell.setCheckTag("1");
        return cellService.updateById(cell);
    }
}

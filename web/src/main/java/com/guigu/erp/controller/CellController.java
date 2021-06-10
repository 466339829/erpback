package com.guigu.erp.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
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

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Cells")
public class CellController {
    @Autowired
    CellService cellService;
    @Autowired
    FileService fileService;
    @RequestMapping("/cesint")
        public boolean cesint(Cell cell, int id){
        System.out.println(id);
        boolean inserts = cellService.inserts(cell, id);
        return  inserts?true:false;

    }
    @RequestMapping("/fuHe")
   @ResponseBody
    public  PageInfo<Cell> fuHe(@RequestParam(defaultValue = "1") int pageNo,
                           @RequestParam(defaultValue = "5") int pageSize,
                           Cell cell){
        PageInfo<Cell> seles = cellService.seles(pageNo, pageSize, cell);
        return seles;
   }
   @RequestMapping("/fuHebyid")
    public Cell fuHebyid(int id){
       return  cellService.getById(id);
   }
   //取消复核修改后删除
    @RequestMapping("/fudeleteid")
    public int fudeleteid(int id){
        return  cellService.deleteas(id);
    }
}

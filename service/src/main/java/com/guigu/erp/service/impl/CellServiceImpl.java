package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.guigu.erp.mapper.CellMapper;
import com.guigu.erp.mapper.FileMapper;
import com.guigu.erp.pojo.Cell;
import com.guigu.erp.pojo.File;
import com.guigu.erp.service.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CellServiceImpl extends ServiceImpl<CellMapper, Cell> implements CellService {
    @Autowired
    CellMapper cellMapper;
    @Autowired
    FileMapper fileMapper;

    @Override
    public boolean inserts(Cell cell, int id) {
        cell.setCheckTag("1");
        int insert = cellMapper.insert(cell);
        if (insert > 0) {
            fileMapper.filsupdate(id);
            return true;
        }
        return false;
    }

//    报错中暂未解决
    @Override
    public int deleteas(int id) {
        int filsupdate = fileMapper.filsupdate(id);
        if (filsupdate>0){
            return  cellMapper.deleteById(id);
        }
        return 1;
    }

    @Override
    public PageInfo<Cell> seles(int pageNo, int pageSize, Cell cell) {
        QueryWrapper<Cell> wrapper = new QueryWrapper<Cell>();
        wrapper.eq("check_tag", "1");
        if (!StringUtil.isEmpty(cell.getProductName())) {
            wrapper.like("product_name", cell.getProductName());
        } else if (!StringUtil.isEmpty(cell.getFirstKindName())) {
            wrapper.like("first_kind_name", cell.getFirstKindName());
        } else if (!StringUtil.isEmpty(cell.getSecondKindName())) {
            wrapper.like("second_kind_name", cell.getSecondKindName());
        } else if (!StringUtil.isEmpty(cell.getThirdKindName())) {
            wrapper.like("third_kind_name", cell.getThirdKindName());
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Cell> list = cellMapper.selectList(wrapper);
        PageInfo<Cell> info = new PageInfo<Cell>(list);
        return info;
    }
}

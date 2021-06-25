package com.guigu.erp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Cell;

public interface CellService extends IService<Cell> {
    public boolean inserts(Cell cell,int id);

    public int deleteas(int id,String productId);
    PageInfo<Cell> seles(int pageNo,int pageSize,Cell cell);

    int selectByBeforeId(String befroeId);

}

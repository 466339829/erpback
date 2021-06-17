package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigu.erp.mapper.ConfigFileKindMapper;
import com.guigu.erp.mapper.FileMapper;
import com.guigu.erp.pojo.ConfigFileKind;
import com.guigu.erp.pojo.File;
import com.guigu.erp.service.FileService;
import com.guigu.erp.util.IDUtil;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ConfigFileKindMapper configFileKindMapper;

    @Override
    public boolean insert(File file) {
        //100+1类编号+2类编号+3类编号+6位流水号
        String longId = fileMapper.getLongId();
        file.setProductId(IDUtil.getProductId(longId, file));

        QueryWrapper<ConfigFileKind> configFileKindQueryWrapper1 = new QueryWrapper<>();
        configFileKindQueryWrapper1.eq("kind_id", file.getFirstKindId());
        ConfigFileKind configFileKind1 = configFileKindMapper.selectOne(configFileKindQueryWrapper1);
        file.setFirstKindName(configFileKind1.getKindName());

        QueryWrapper<ConfigFileKind> configFileKindQueryWrapper2 = new QueryWrapper<>();
        configFileKindQueryWrapper2.eq("kind_id", file.getSecondKindId());
        ConfigFileKind configFileKind2 = configFileKindMapper.selectOne(configFileKindQueryWrapper2);
        file.setSecondKindName(configFileKind2.getKindName());

        QueryWrapper<ConfigFileKind> configFileKindQueryWrapper3 = new QueryWrapper<>();
        configFileKindQueryWrapper3.eq("kind_id", file.getThirdKindId());
        ConfigFileKind configFileKind3 = configFileKindMapper.selectOne(configFileKindQueryWrapper3);
        file.setThirdKindName(configFileKind3.getKindName());

        return this.save(file);
    }

    //复核通过
    @Override
    public boolean checkTag(int id,String checker) {
        File file = this.getById(id);
        file.setCheckTag("1");
        //复核人
        file.setChecker(checker);
        //复核时间
        file.setCheckTime(new Date());
        return this.updateById(file);
    }

    @Override
    public boolean updateFile(File file) {
        //未复核
        file.setCheckTag("0");
        //档案变更标志
        file.setChangeTag("1");
        /*//变更时间
        file.setChangeTime(new Date());*/
        //产品档案每变更一次,则file_change_amount加1
        file.setFileChangeAmount(file.getFileChangeAmount() + 1);
        return this.updateById(file);
    }

    @Override
    public ResultUtil deleteById(int id) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<File> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("id", id);
        File file = this.getOne(usersQueryWrapper);
        file.setDeleteTag("1");
        boolean result = this.updateById(file);
        if (result) {
            resultUtil.setResult(true);
            resultUtil.setMessage("删除成功");
            return resultUtil;
        } else
            resultUtil.setResult(false);
            resultUtil.setMessage("删除失败");
            return resultUtil;
    }

    @Override
    public ResultUtil foreverDelete(int id) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<File> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("id", id);
        File file = this.getOne(usersQueryWrapper);
        file.setDeleteTag("2");
        boolean result = this.updateById(file);
        if (result) {
            resultUtil.setResult(true);
            resultUtil.setMessage("删除成功");
            return resultUtil;
        } else
            resultUtil.setResult(false);
        resultUtil.setMessage("删除失败");
        return resultUtil;
    }

    @Override
    public ResultUtil recoveryById(int id) {
        ResultUtil<Object> resultUtil = new ResultUtil<>();
        QueryWrapper<File> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("id", id);
        File file = this.getOne(usersQueryWrapper);
        file.setCheckTag("0");
        file.setDeleteTag("0");
        file.setFileChangeAmount(file.getFileChangeAmount() + 1);
        boolean result = this.updateById(file);
        if (result) {
            resultUtil.setResult(true);
            resultUtil.setMessage("恢复成功");
            return resultUtil;
        } else
            resultUtil.setResult(false);
            resultUtil.setMessage("恢复失败");
            return resultUtil;
    }

    @Override
    public ResultUtil checkName(String name) {
        ResultUtil<File> resultUtil = new ResultUtil<>();
        //判断产品名是否存在
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_name", name);
        File file = this.getOne(queryWrapper);
        if (file != null && !"2".equals(file.getDeleteTag())) {
            resultUtil.setMessage("用户名已存在");
            resultUtil.setResult(false);
            return resultUtil;
        } else {
            resultUtil.setMessage("用户名可以使用");
            resultUtil.setResult(true);
            return resultUtil;
        }
    }

    @Override
    public PageInfo queryPage(int pageNo, int pageSize, File file) {
        List<File> fileList = null;
        if (file != null) {
            //追加条件 产品名称
            QueryWrapper<File> queryWrapper = new QueryWrapper<>();
            if (file.getProductName() != null && file.getProductName() != "")
                queryWrapper.like("product_name", file.getProductName());
           // 追加条件 产品i级分类编号
            if (file.getFirstKindId() != null && file.getFirstKindId() != "")
                queryWrapper.eq("first_kind_id", file.getFirstKindId());
            // 追加条件 产品ii级分类编号
            if (file.getSecondKindId() != null && file.getSecondKindId() != "")
                queryWrapper.eq("second_kind_id", file.getSecondKindId());
            // 追加条件 产品iii级分类编号
            if (file.getThirdKindId() != null && file.getThirdKindId() != "")
                queryWrapper.eq("third_kind_id", file.getThirdKindId());
            // 追加条件 建档时间开始
            if (file.getRegisterTime() != null)
                queryWrapper.ge("register_time", file.getRegisterTime());
            // 追加条件 建档时间结束
            if (file.getRegisterTime2() != null )
                queryWrapper.le("register_time", file.getRegisterTime2());
            //审核标志0: 等待1: 通过2: 不通过
            if (file.getCheckTag() != null && file.getCheckTag() != "")
                queryWrapper.eq("check_tag", file.getCheckTag());
             //产品删除标志0: 未删除1: 已删除2永久删除
            if (file.getDeleteTag() != null && file.getDeleteTag() != "")
                queryWrapper.eq("delete_tag", file.getDeleteTag());
            //类型
            if (file.getType() != null && file.getType() != "")
                queryWrapper.eq("type", file.getType());
            //design_module_tag物料组成标志0    designModuleTag:1,
            if (file.getDesignModuleTag() != null && file.getDesignModuleTag() != "")
                queryWrapper.eq("design_module_tag", file.getDesignModuleTag());
            // designProcedureTag:1,
            if (file.getDesignProcedureTag() != null && file.getDesignProcedureTag() != "")
                queryWrapper.eq("design_procedure_tag", file.getDesignProcedureTag());
            PageHelper.startPage(pageNo, pageSize);
            fileList = this.list(queryWrapper);
        } else {
            PageHelper.startPage(pageNo, pageSize);
            fileList = this.list();
        }
        return new PageInfo<File>(fileList);
    }
}

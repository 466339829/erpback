package com.guigu.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.erp.mapper.UploadMapper;
import com.guigu.erp.pojo.Upload;
import com.guigu.erp.service.UploadService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class UploadServiceImpl extends ServiceImpl<UploadMapper, Upload> implements UploadService {
    @Override
    public ResultUtil upload(Upload upload, MultipartFile img, HttpServletRequest request) {
        ResultUtil<Upload> resultUtil = new ResultUtil<>();
        Upload result = fileUpload(upload, img,request);
        boolean save = this.save(result);
        if (save){
            resultUtil.setData(result);
            resultUtil.setMessage("上传成功");
            resultUtil.setResult(true);
            return resultUtil;
        }
        resultUtil.setMessage("上传失败");
        resultUtil.setResult(false);
        return resultUtil;
    }
    public Upload fileUpload(Upload upload, MultipartFile img, HttpServletRequest request) {
        // 获取原始文件名
        String originalFilename = img.getOriginalFilename();
        // 获取后缀名
        String suffixname = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 生成新文件名
        String temp = UUID.randomUUID().toString();
        String filename = temp + suffixname;
        // 创建指定的目录
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dirname = simpleDateFormat.format(now);
        //上传文件地址
        //应用上下文对象  request.getServletContext()
        String path = request.getServletContext().getRealPath("/images");
        File destFile = new File(path + "/" + dirname + "/" + filename);
        if (!destFile.exists())
            destFile.mkdirs();
        try {
            img.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置用户的图片地址
        upload.setPath(dirname + "/" + filename);
        return upload;
    }
}

package com.guigu.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.erp.pojo.Upload;
import com.guigu.erp.util.ResultUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UploadService extends IService<Upload> {
    ResultUtil upload(Upload upload, MultipartFile img, HttpServletRequest request);
}

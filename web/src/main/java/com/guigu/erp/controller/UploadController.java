package com.guigu.erp.controller;

import com.guigu.erp.pojo.Upload;
import com.guigu.erp.service.UploadService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
@RequestMapping("/file")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    //文件上传
    @RequestMapping(value = "/upload", produces = "application/json;charset=utf-8")
    public ResultUtil<Upload> insert(Upload upload, MultipartFile file, HttpServletRequest request) {
        return uploadService.upload(upload,file,request);
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable int id){
        return  uploadService.removeById(id);
    }
}

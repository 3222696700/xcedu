package com.xuecheng.filesystem.controller;

import com.xuecheng.api.filesystem.FileSystemControllerApi;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/7/13
 * @Description:com.xuecheng.filesystem.controller
 * @version:1.0
 */
public class FileSystemController implements FileSystemControllerApi {
    @Autowired
    FileSystemService fileSystemService;
    @Override
    public UploadFileResult uploadFile(MultipartFile multipartFile, Map otherPropertisMap) {
        return null;
    }
}

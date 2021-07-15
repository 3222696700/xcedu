package com.xuecheng.filesystem.controller;

import com.xuecheng.api.filesystem.FileSystemControllerApi;
import com.xuecheng.framework.domain.filesystem.request.FileSystemRequest;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther:ghost
 * @Date:2021/7/14
 * @Description:com.xuecheng.filesystem.controller
 * @version:1.0
 */
public class FileSystemController implements FileSystemControllerApi {
    @Override
    public UploadFileResult uploadFile(MultipartFile multipartFile, FileSystemRequest fileSystemRequest) {
        return null;
    }

    @Override
    public UploadFileResult downFile(MultipartFile multipartFile, FileSystemRequest fileSystemRequest) {
        return null;
    }

}

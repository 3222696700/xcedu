package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.request.FileSystemRequest;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther:ghost
 * @Date:2021/7/13
 * @Description:com.xuecheng.api.filesystem
 * @version:1.0
 */
@Api(value="文件管理系统接口")
public interface FileSystemControllerApi {

    @ApiOperation("文件上传")
    UploadFileResult uploadFile(MultipartFile multipartFile, FileSystemRequest fileSystemRequest);

}

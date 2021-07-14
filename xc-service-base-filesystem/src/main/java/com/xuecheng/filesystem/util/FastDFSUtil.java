package com.xuecheng.filesystem.util;

import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.exception.ExceptionCast;
import lombok.Data;
import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Auther:ghost
 * @Date:2021/7/13
 * @Description:com.xuecheng.filesystem.util
 * @version:1.0
 */
@Component
@Data
public class FastDFSUtil<T> {


    @Autowired
    private StorageClient1 storageClient1;

//    上传文件
    public String uploadFile(MultipartFile multipartFile) {
           try {

               if(multipartFile==null){
                   ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
               }
               //得到文件字节
               byte[] bytes = multipartFile.getBytes();

               //得到文件的原始名称
               String originalFilename = multipartFile.getOriginalFilename();

               //得到文件扩展名
               assert originalFilename != null;

               String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

               return storageClient1.upload_file1(bytes, ext, null);

           } catch (IOException | MyException e) {
               e.printStackTrace();
               ExceptionCast.cast(FileSystemCode.FS_SYSTEM_INIT_ERROR);
           }
        return null;
    }

}





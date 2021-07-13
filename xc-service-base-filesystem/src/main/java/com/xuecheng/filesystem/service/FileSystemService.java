package com.xuecheng.filesystem.service;

import com.xuecheng.filesystem.dao.FIleSystemRepository;
import com.xuecheng.filesystem.util.FastDFSUtil;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/7/13
 * @Description:com.xuecheng.filesystem.service
 * @version:1.0
 */
@Service
public class FileSystemService
{

    @Autowired
    FIleSystemRepository fIleSystemRepository;

    @Autowired
    FastDFSUtil fastDFSUtil;


    @Transactional
    //这里是将客户端的要求，封装成了一个Map
    public UploadFileResult uploadFile(MultipartFile multipartFile, Map<String,Object> map){
        String fileId=uploadFileToFastFS(multipartFile);

        if(StringUtils.isEmpty(fileId)){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);

            return new UploadFileResult(CommonCode.FAIL, null);
        }
        FileSystem fileSystem=savaFileSysTemToMongodb(fileId,map,multipartFile);
        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
    }

    public String uploadFileToFastFS(MultipartFile multipartFile){

        return fastDFSUtil.uploadFile(multipartFile);
    }

    public FileSystem savaFileSysTemToMongodb(String fileId,Map map,MultipartFile multipartFile){
        FileSystem fileSystem=new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFileSize(multipartFile.getSize());
        fileSystem.setFiletag((String)map.get("filetag"));
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());
        fileSystem.setMetadata((Map) map.get("metadata"));
        fileSystem.setBusinesskey((String)(map.get("businesskey")));
        return fIleSystemRepository.save(fileSystem);
    }
}






package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.filesystem.dao.FIleSystemRepository;
import com.xuecheng.filesystem.util.FastDFSUtil;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.request.FileSystemRequest;
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
    //
    public UploadFileResult uploadFile(MultipartFile multipartFile, FileSystemRequest fileSystemRequest){
        String fileId=uploadFileToFastFS(multipartFile);

        if(StringUtils.isEmpty(fileId)){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
            return new UploadFileResult(CommonCode.FAIL, null);
        }
        FileSystem fileSystem=savaFileSysTemToMongodb(fileId,fileSystemRequest,multipartFile);
        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
    }

    public String uploadFileToFastFS(MultipartFile multipartFile){

        return fastDFSUtil.uploadFile(multipartFile);
    }

    public FileSystem savaFileSysTemToMongodb(String fileId,FileSystemRequest fileSystemRequest,MultipartFile multipartFile){
        FileSystem fileSystem=new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFileSize(multipartFile.getSize());
        fileSystem.setFiletag(fileSystemRequest.getFiletag());
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setFileType(multipartFile.getContentType());
        String metaData=fileSystemRequest.getMetadata();

        if(!(StringUtils.isEmpty(metaData))){
            Map map= JSON.parseObject(metaData,Map.class);
            fileSystem.setMetadata(map);
        }
        fileSystem.setBusinesskey(fileSystemRequest.getBusinesskey());
        return fIleSystemRepository.save(fileSystem);
    }
}






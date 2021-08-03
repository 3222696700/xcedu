package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.CmsTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.mapper.CmsTemplateRepository;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Auther:ghost
 * @Date:2021/7/15
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@Service
public class CmsTemplateService {
    @Resource
    CmsTemplateRepository cmsTemplateRepository;

    @Resource
    GridFsTemplate gridFsTemplate;

    @Resource
    GridFSBucket gridFSBucket;



    public CmsTemplate saveTemplate(MultipartFile multipartFile, CmsTemplateRequest cmsTemplateRequest) {
        if(multipartFile==null
                ||cmsTemplateRequest==null
                ||StringUtils.isEmpty(cmsTemplateRequest.getTemplateName())
                || StringUtils.isEmpty(cmsTemplateRequest.getSiteId())){
            return null;
        }
        String fileid=saveTemplatePage(multipartFile);

        if (StringUtils.isEmpty(fileid)) {
            return null;
        }
        CmsTemplate cmsTemplate = new CmsTemplate();
        cmsTemplateRequest.setTemplateFileId(fileid);
        BeanUtils.copyProperties(cmsTemplateRequest,cmsTemplate);
        CmsTemplate saveTemplate=cmsTemplateRepository.save(cmsTemplate);
        return saveTemplate;
    }

    /**
     * @Description: 模板文件保存和更新
     */
    public String saveTemplatePage(MultipartFile multipartFile) {
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            ObjectId objectId = gridFsTemplate.store(inputStream, multipartFile.getName());
            return objectId.toHexString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param templateFileId 发布页面静态页面存放的templateFileId
     * @Description: 根据htmlFileId获取静态化页面文件
     * @Author: ghost
     * @Date:2021/6/22
     */
    public String getTemplateFileByTemplateFileId(String templateFileId) {

        if(StringUtils.isEmpty(templateFileId)){
            return null;
        }
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        if (gridFSFile == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
            return null;
        }
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        String content;
        try {
            content = IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
                e.printStackTrace();
                return null;
        } finally {
            gridFSDownloadStream.close();
        }
        return content;

    }

    public ResponseResult deletTemplateBase(String templateId) {
        if (StringUtils.isEmpty(templateId)) {
            return new ResponseResult(CommonCode.FAIL);
        }
        CmsTemplate cmsTemplate=cmsTemplateRepository.findById(templateId).orElse(null);
        if(cmsTemplate==null||StringUtils.isEmpty(cmsTemplate.getTemplateFileId())){
            return new ResponseResult(CommonCode.FAIL);
        }
        gridFSBucket.delete(new ObjectId(cmsTemplate.getTemplateFileId()));

        cmsTemplateRepository.deleteById(templateId);

        return new ResponseResult(CommonCode.SUCCESS);
    }
}

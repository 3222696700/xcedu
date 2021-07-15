package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.xuecheng.manage_cms.mapper.CmsTemplateRepository;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}

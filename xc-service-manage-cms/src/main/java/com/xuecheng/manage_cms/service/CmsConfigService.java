package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.mapper.CmsConfigRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/16
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@Service
public class CmsConfigService {

    @Resource
    CmsConfigRepository cmsConfigRepository;

    public CmsConfig getCmsConfigById(String id){
       return cmsConfigRepository.findById(id).orElse(null);
    }

    public ResponseResult saveCmsConfig(CmsConfig cmsConfig) {
        return cmsConfigRepository.save(cmsConfig)==null?ResponseResult.FAIL():ResponseResult.SUCCESS();
    }
}

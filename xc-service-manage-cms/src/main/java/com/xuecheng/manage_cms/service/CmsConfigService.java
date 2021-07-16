package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.mapper.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther:ghost
 * @Date:2021/7/16
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@Service
public class CmsConfigService {

    @Autowired
    CmsConfigRepository cmsConfigRepository;



    public CmsConfig getCmsConfigById(String id){
       return cmsConfigRepository.findById(id).orElse(null);
    }
}

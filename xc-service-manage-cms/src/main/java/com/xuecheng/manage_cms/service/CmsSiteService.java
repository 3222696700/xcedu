package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms.mapper.CmsSiteRepository;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
public class CmsSiteService {

    @Resource
    CmsSiteRepository cmsSiteRepository;

    public CmsSite findCmsSiteBySiteId(String siteId) {
        return cmsSiteRepository.findById(siteId).orElse(null);
    }
}

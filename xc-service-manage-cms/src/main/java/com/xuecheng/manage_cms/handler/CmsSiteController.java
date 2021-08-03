package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.CmsSiteControllerApi;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms.service.CmsSiteService;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
public class CmsSiteController implements CmsSiteControllerApi {

    @Resource
    CmsSiteService cmsSiteService;

    @Override
    public CmsSite findCmsSiteBySiteId(String siteId) {
        return cmsSiteService.findCmsSiteBySiteId(siteId);
    }
}

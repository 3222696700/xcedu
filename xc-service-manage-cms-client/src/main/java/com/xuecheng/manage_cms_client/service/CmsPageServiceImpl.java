package com.xuecheng.manage_cms_client.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms_client.mapper.CmsPageRepository;
import com.xuecheng.manage_cms_client.mapper.CmsSiteRepository;
import com.xuecheng.manage_cms_client.util.FreeMarkerUtil;
import com.xuecheng.manage_cms_client.util.GridFSUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Auther:ghost
 * @Date:2021/9/11
 * @Description:com.xuecheng.service
 * @version:1.0
 */
@Service
public class CmsPageServiceImpl {

    @Resource
    GridFSUtils gridFSUtils;

    @Resource
    CmsPageRepository cmsPageRepository;

    @Resource
    CmsSiteRepository cmsSiteRepository;

    public boolean savePageToServerPath(String pageId) {
        if (StringUtils.isEmpty(pageId)) {
            return false;
        }

        CmsPage cmsPage = cmsPageRepository.findById(pageId).orElse(null);
        if (cmsPage == null) {
            return false;
        }
        String pageServerPath = getPageServerPath(cmsPage);

        if (StringUtils.isEmpty(pageServerPath)) {
            return false;
        }
        String htmlFileId = cmsPage.getHtmlFileId();

        InputStream inputStream = null;
        try {
            inputStream = gridFSUtils.downloadFileFromMongoDB(htmlFileId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FreeMarkerUtil.uploadePageToServer(inputStream, pageServerPath);
    }

    /**
     * @param cmsPage 发布页面
     * @Description:根据CmsPage获取页面保存的节点物理路径
     * @Author: ghost
     * @Date:2021/6/22
     */
    public String getPageServerPath(CmsPage cmsPage) {
        if (cmsPage == null
                || StringUtils.isEmpty(cmsPage.getSiteId())
                || StringUtils.isEmpty(cmsPage.getPagePhysicalPath())
                || StringUtils.isEmpty(cmsPage.getPageName())) {
            return null;
        }
        CmsSite cmsSite = cmsSiteRepository.findById(cmsPage.getSiteId()).orElse(null);
        if (cmsSite == null || StringUtils.isEmpty(cmsSite.getSitePhysicalPath())) {
            return null;
        }
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();
        return sitePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
    }
}

package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms_client.mapper.CmsPageRepository;
import com.xuecheng.manage_cms_client.mapper.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Optional;

/**
 * @Auther:ghost
 * @Date:2021/7/6
 * @Description:com.xuecheng.manage_cms_client.service
 * @version:1.0
 */
@Service
public class CmsPageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsPageService.class);

    @Resource
    CmsPageRepository cmsPageRepository;

    @Resource
    CmsSiteRepository cmsSiteRepository;

    @Resource
    GridFsTemplate gridFsTemplate;

    @Resource
    GridFSBucket gridFSBucket;



    /**
     * @param pageId    发布页面的pageId
     * @Description: 根据提供的pageId将指定静态化后页面保存至页面配置指定路径下
     * 1、根据pageId查询CmsPage,获取页面的siteId和htmlFileId
     * 2、根据htmlFileId获取静态化页面文件
     * 3、根据siteId获取页面保存的节点物理路径
     * 4、根据路径=节点物理路径+页面物理路径+页面名称拼装静态页面保存路径
     * 5、保存静态化页面至指定路径。
     * @Author: ghost
     * @Date:2021/6/22
     */
    public void savePageToServerPath(String pageId) {

        CmsPage cmsPage = this.findCmsPageById(pageId);

        String htmlFileId = cmsPage.getHtmlFileId();

        InputStream inputStream = this.getFileById(htmlFileId);

        if (inputStream == null) {
            LOGGER.error("getFileById() return inputStream is null,getHtmlFileId:{}", htmlFileId);
            return;
        }

        String siteId = cmsPage.getSiteId();

        String sitePhysicalPath = this.findSiteBySiteId(siteId);

        String pagePath = sitePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    /**
     * @param pageId    发布页面的pageId
     * @Description: 根据pageId查询CmsPage,获取页面的siteId和htmlFileId
     * @Author: ghost
     * @Date:2021/6/22
     */
    public CmsPage findCmsPageById(String pageId) {

        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        return optional.orElse(null);
    }

    /**
     * @param templateFileId    发布页面静态页面存放的templateFileId
     * @Description: 根据htmlFileId获取静态化页面文件
     * @Author: ghost
     * @Date:2021/6/22
     */
    private InputStream getFileById(String templateFileId) {

        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));

        assert gridFSFile != null;

        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @param siteId    发布页面所属站点Id
     * @Description: 根据siteId获取页面保存的节点物理路径。
     * @Author: ghost
     * @Date:2021/6/22
     */
    private String findSiteBySiteId(String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        return optional.map(CmsSite::getSitePhysicalPath).orElse(null);
    }

}

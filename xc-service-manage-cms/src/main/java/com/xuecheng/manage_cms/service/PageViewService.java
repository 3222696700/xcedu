package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPagePostResult;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_cms.util.FreeMarkerUtil;
import com.xuecheng.manage_cms.util.PageViewUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther:ghost
 * @Date:2021/7/31
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@Service
public class PageViewService {

    @Resource
    CmsTemplateService cmsTemplateService;

    @Resource
    CmsSiteService cmsSiteService;

    @Resource
    CmsPageService cmsPageService;

   @Resource
    GridFsTemplate gridFsTemplate;

    @Resource
    RabbitTemplate rabbitTemplate;

    /**
     * @param id 预览页面的Id
     * @Description:页面预览
     * @Author: ghost
     * @Date:2021/6/22
     */
    public String previewPage(String id) {
        if (StringUtils.isEmpty(id)) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        CmsPage cmsPage = cmsPageService.findCmsPageById(id);

        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        CmsPageResult cmsPageResult = cmsPageService.savePage(cmsPage);

        if (!cmsPageResult.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        CmsPage page = cmsPageResult.getCmsPage();

        return gennerateHtml(page);
    }

    @Transactional
    public CmsPagePostResult postCmsPage(String pageId) {
        if (StringUtils.isEmpty(pageId)) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        CmsPage cmsPage = cmsPageService.findCmsPageById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        String url = getPostPageUrl(cmsPage);
        if (gennerateAndSaveHtml(cmsPage)){
            Map map=new ConcurrentHashMap<>();
            map.put("pageId", pageId);
            rabbitTemplate.convertAndSend(map);
            return CmsPagePostResult.postSuccess(url);
        }
        return CmsPagePostResult.postFail();
    }

    /**
     * @param cmsPage 发布页面的Id
     * @Description:
     * @Author: ghost
     * @Date:2021/6/22
     */

    public boolean gennerateAndSaveHtml(CmsPage cmsPage) {

        String htmlPage = gennerateHtml(cmsPage);

        InputStream inputStream = IOUtils.toInputStream(htmlPage, StandardCharsets.UTF_8);

        ObjectId objectId= gridFsTemplate.store(inputStream,cmsPage.getPageName());

        cmsPage.setHtmlFileId(objectId.toHexString());

        CmsPageResult cmsPageResult = cmsPageService.savePage(cmsPage);

        return null != cmsPageResult && cmsPageResult.isSuccess() && (null != cmsPageResult.getCmsPage());
    }

    /**
     * @param cmsPage 发布页面
     * @Description:根据CmsPage生成渲染的HTML
     * @Author: ghost
     * @Date:2021/6/22
     */
    public String gennerateHtml(CmsPage cmsPage) {
        if (StringUtils.isEmpty(cmsPage.getDataUrl())
                || StringUtils.isEmpty(cmsPage.getHtmlFileId())
                || StringUtils.isEmpty(cmsPage.getSiteId())) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        Map model = PageViewUtil.getModelByDataUrl(cmsPage.getDataUrl());

        if (CollectionUtils.isEmpty(model)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        String template = cmsTemplateService.getTemplateFileByTemplateFileId(cmsPage.getHtmlFileId());

        if (template == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        String html = FreeMarkerUtil.gennerateHtml(template, model);

        if (StringUtils.isEmpty(html)) {
            return null;
        }
        return html;
    }

    public String getPostPageUrl(CmsPage cmsPage) {
        if (cmsPage == null || StringUtils.isEmpty(cmsPage.getSiteId())) {
            return "";
        }
        CmsSite cmsSite = cmsSiteService.findCmsSiteBySiteId(cmsPage.getSiteId());
        assert cmsSite!=null;
        return cmsSite.getSiteDomain() + cmsSite.getSiteWebPath() + cmsPage.getPageWebPath() + cmsPage.getPageName();
    }
}

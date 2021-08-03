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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Map;

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

    // todo:这里其实可以提供一个通用的获取页面预览URL、页面预览和页面发布接口，但是目前业务场景不允许，后续待实现。

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


    /**
     * @param id 发布页面的Id
     * @Description:页面发布
     * @Author: ghost
     * @Date:2021/6/22
     */

    // todo:页面保存到服务器可以使用消息服务异步调用，待实现。
    public CmsPagePostResult postCmsPage(String id) {
        if (StringUtils.isEmpty(id)) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        CmsPage cmsPage = cmsPageService.findCmsPageById(id);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        return savePageToServerPath(cmsPage)?new CmsPagePostResult(CommonCode.FAIL,""):new CmsPagePostResult(CommonCode.SUCCESS,genneratePageUrl(cmsPage));
    }

    public String genneratePageUrl(CmsPage cmsPage){
        if (cmsPage == null||StringUtils.isEmpty(cmsPage.getPageWebPath())) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        CmsSite cmsSite=cmsSiteService.findCmsSiteBySiteId(cmsPage.getSiteId());
        if(cmsSite==null||StringUtils.isEmpty(cmsSite.getSiteDomain())||StringUtils.isEmpty(cmsSite.getSiteWebPath())){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //页面url=站点域名+站点webpath+页面webpath+页面名称
        return cmsSite.getSiteDomain()+cmsSite.getSiteWebPath()+ cmsPage.getPageWebPath()+cmsPage.getPageName();
    }

    /**
     * @param cmsPage 发布页面
     * @Description:根据CmsPage生成渲染的HTML
     * @Author: ghost
     * @Date:2021/6/22
     */
    public String gennerateHtml(CmsPage cmsPage) {
        if (StringUtils.isEmpty(cmsPage.getDataUrl()) || StringUtils.isEmpty(cmsPage.getHtmlFileId()) || StringUtils.isEmpty(cmsPage.getSiteId())) {
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

    /**
     * @paramc cmsPage 发布页面信息
     * @Description: 根据提供的pageId将指定静态化后页面保存至页面配置指定路径下
     * @Author: ghost
     * @Date:2021/6/22
     */
    public boolean savePageToServerPath(CmsPage cmsPage) {
        if (cmsPage == null
                || StringUtils.isEmpty(cmsPage.getHtmlFileId())
                || StringUtils.isEmpty(cmsPage.getSiteId())
                || StringUtils.isEmpty(cmsPage.getPagePhysicalPath())
                || StringUtils.isEmpty(cmsPage.getPageName())
                || StringUtils.isEmpty(cmsPage.getDataUrl())) {
            return false;
        }
        String pageServerPath = getPageServerPath(cmsPage);
        if (StringUtils.isEmpty(pageServerPath)) {
            return false;
        }
        String html = gennerateHtml(cmsPage);
        return FreeMarkerUtil.uploadePageToServer(html, pageServerPath);
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
        CmsSite cmsSite = cmsSiteService.findCmsSiteBySiteId(cmsPage.getSiteId());
        if (cmsSite == null || StringUtils.isEmpty(cmsSite.getSitePhysicalPath())) {
            return null;
        }
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();
        return sitePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
    }


}

package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.mapper.CmsPageRepository;
import com.xuecheng.manage_cms.mapper.CmsSiteRepository;
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
    CmsSiteRepository cmsSiteRepository;

    @Resource
    CmsPageRepository cmsPageRepository;

    /**
     * @param pageId 静态化页面的id
     * @return String : 静态化页面。
     * @Description: 页面预览
     * @Author: ghost
     * @Date:2021/6/22
     */

    public String getPageHtml(String pageId) {
        if(StringUtils.isEmpty(pageId)){
            return null;
        }
        CmsPage cmsPage=cmsPageRepository.findById(pageId).orElse(null);

        if(cmsPage==null||StringUtils.isEmpty(cmsPage.getDataUrl())||StringUtils.isEmpty(cmsPage.getHtmlFileId())){
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



    /**@Description: 页面发布
     * @param pageId 发布页面的pageId
     * @Author: ghost
     * @Date:2021/6/22
     */
    public ResponseResult postCmsPage(String pageId){
        if(StringUtils.isEmpty(pageId)){
            return new ResponseResult(CommonCode.FAIL);
        }
        return savePageToServerPath(pageId)?new ResponseResult(CommonCode.SUCCESS):new ResponseResult(CommonCode.FAIL);
    }
    /**
     * @param pageId 发布页面的pageId
     * @Description: 根据提供的pageId将指定静态化后页面保存至页面配置指定路径下
     * @Author: ghost
     * @Date:2021/6/22
     */
    public boolean savePageToServerPath(String pageId) {
        if(StringUtils.isEmpty(pageId)){
            return false;
        }
        CmsPage cmsPage =cmsPageRepository.findById(pageId).orElse(null);
        if(cmsPage==null
                ||StringUtils.isEmpty(cmsPage.getHtmlFileId())
                ||StringUtils.isEmpty(cmsPage.getSiteId())
                ||StringUtils.isEmpty(cmsPage.getPagePhysicalPath())
                ||StringUtils.isEmpty(cmsPage.getPageName())
                ||StringUtils.isEmpty(cmsPage.getDataUrl())){
            return false;
        }
        String pageServerPath=getPageServerPath(cmsPage);
        if(StringUtils.isEmpty(pageServerPath)){
            return false;
        }
        String content=cmsTemplateService.getTemplateFileByTemplateFileId(cmsPage.getHtmlFileId());

        Map data= PageViewUtil.getModelByDataUrl(cmsPage.getDataUrl());

        if(StringUtils.isEmpty(content)|| CollectionUtils.isEmpty(data)){
            return false;
        }
        String html=FreeMarkerUtil.gennerateHtml(content,data);
        return FreeMarkerUtil.uploadePageToServer(html,pageServerPath);
    }

    /**
     *
     * @Description:根据CmsPage获取页面保存的节点物理路径
     * @param cmsPage 发布页面
     * @Author: ghost
     * @Date:2021/6/22
     */
    public String getPageServerPath(CmsPage cmsPage){
        CmsSite cmsSite = cmsSiteRepository.findById(cmsPage.getSiteId()).orElse(null);
        if (cmsSite==null|| StringUtils.isEmpty(cmsSite.getSitePhysicalPath())){
            return null;
        }
        String sitePhysicalPath=cmsSite.getSitePhysicalPath();
        return sitePhysicalPath +cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
    }
}

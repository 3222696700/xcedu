package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.mapper.CmsPageRepository;
import com.xuecheng.manage_cms.mapper.CmsSiteRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * @Auther:ghost
 * @Date:2021/6/14
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
/*
 * cmsPage页码从第一页开始计数，pageable的页码从0开始
 *
 *
 *
 *
 * */
@Service
public class CmsPageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CmsPageService.class);

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Resource
    CmsPageRepository cmsPageRepository;


    @Resource
    CmsTemplateService templateService;


    @Resource
    CmsConfigService cmsConfigService;

    @Autowired
    CmsTemplateService cmsTemplateService;

    /**
     * @param size             每页记录数
     * @param page             分页页码：日常习惯从1开始，pageable从0开始。
     * @param queryPageRequest :页面查询条件
     * @return queryResponseResult : 统一数据返回对象。
     * @Description: 分页查询MongoDB数据库的页面数据
     * @Author: ghost
     * @Date:2021/6/14
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {

        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        CmsPage cmsPage = new CmsPage();

        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageId())) {
            cmsPage.setPageId(queryPageRequest.getPageId());
        }

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        if (page <= 0) {
            page = 1;
        }
        page = page - 1;

        if (size <= 0) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<CmsPage> pages = cmsPageRepository.findAll(example, pageable);

        QueryResult queryResult = new QueryResult();

        queryResult.setList(pages.getContent());

        queryResult.setTotal(pages.getTotalElements());

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * @param cmsPage 新增页面信息
     * @return CmsPageResult : 统一数据返回对象。
     * @Description: 新增CmsPage对象
     * @Author: ghost
     * @Date:2021/6/22
     */
    public CmsPageResult add(CmsPage cmsPage) {

//        参数校验
        if (cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //页面唯一性校验
        CmsPage currentPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),
                cmsPage.getSiteId(), cmsPage.getPageWebPath());

        if (currentPage != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        } else {
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }

        return new CmsPageResult(CommonCode.FAIL, null);
    }



    /**
     * @param id      更新CmsPage对象id
     * @param cmsPage 更新CmsPage对象信息
     * @return CmsPageResult : 统一数据返回对象。
     * @Description: 更新指定Id的CmsPage对象
     * @Author: ghost
     * @Date:2021/6/22
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage oldCmsPage = cmsPageRepository.findById(id).orElse(null);

        if (oldCmsPage != null) {

            //更新模板Id
            oldCmsPage.setTemplateId(cmsPage.getTemplateId());
            //更新站点Id
            oldCmsPage.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            oldCmsPage.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            oldCmsPage.setPageName(cmsPage.getPageName());
            //更新访问路径
            oldCmsPage.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            oldCmsPage.setPagePhysicalPath(cmsPage.getPagePhysicalPath());

            CmsPage newCmsPage = cmsPageRepository.save(oldCmsPage);

            return new CmsPageResult(CommonCode.SUCCESS, newCmsPage);

        }

        return new CmsPageResult(CommonCode.FAIL, null);

    }

    /**
     * @param id 删除CmsPage对象id
     * @return ResponseResult : 统一数据返回对象。
     * @Description: 删除指定Id的CmsPage对象
     * @Author: ghost
     * @Date:2021/6/22
     */
    public ResponseResult delete(String id) {

        Optional<CmsPage> optional = cmsPageRepository.findById(id);

        if (optional.isPresent()) {

            cmsPageRepository.deleteById(id);

            return new ResponseResult(CommonCode.SUCCESS);

        }

        return new ResponseResult(CommonCode.FAIL);

    }
//=================================================================页面预览=======================================================
    /**
     * @param pageId 静态化页面的id
     * @return String : 静态化页面。
     * @Description: 页面静态化
     * 1、getModelByPageId(pageId)--根据pageId获取CmsPage中的DataUrl,请求DataUrl获取页面的CmsConfig(页面数据)。
     * 2、getTemplateBypageId(pageId)--根据pageId获取CmsTemplate对象的TemplateFileId,通过GriFS提供的API将对相应TemplateFileId的MongoDB文件读取。
     * 3、将页面数据和页面文件（图片等）进行渲染，输出静态HTML。
     * @Author: ghost
     * @Date:2021/6/22
     */

    public String getPageHtml(String pageId) {
        if(StringUtils.isEmpty(pageId)){
            return null;
        }
        Map model = this.getModelByPageId(pageId);

        if (CollectionUtils.isEmpty(model)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        String template = this.getTemplateBypageId(pageId);
        if (template == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String html = this.gennerateHtml(template, model);
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        return html;
    }

    /**
     * @param pageId 静态化页面的id
     * @return Map : 根据pageId获得的PageConfig对象中URL并请求该接口的页面配置返回值Map集合。
     * @Description: 页面静态化
     * @Author: ghost
     * @Date:2021/6/22
     */
    private Map getModelByPageId(String pageId) {
        CmsPage cmsPage = cmsPageRepository.findById(pageId).orElse(null);
        if (cmsPage == null||StringUtils.isEmpty(cmsPage.getConfigid())) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        }
        CmsConfig cmsConfig=cmsConfigService.getCmsConfigById(cmsPage.getConfigid());
        if(cmsConfig==null){
            return null;
        }
        return JSONObject.parseObject(JSON.toJSONString(cmsConfig));
    }

    /**
     * @param pageId 静态化页面的id
     * @return String : 静态化页面。
     * @Description: 根据提供的pageId获取页面对应的templateId, 并通过GridFSBucket加载模板文件
     * @Author: ghost
     * @Date:2021/6/22
     */
    private String getTemplateBypageId(String pageId) {
        CmsPage cmsPage =cmsPageRepository.findById(pageId).orElse(null);
        if (cmsPage == null||StringUtils.isEmpty(cmsPage.getTemplateId())) {
//         根据pageId无法查询到页面时抛出异常。
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        }
       return templateService.getTemplateFileByTemplateFileId(cmsPage.getHtmlFileId());
    }

    /**
     * @param template 静态化页面的模板
     * @param data     静态化页面的数据
     * @return String : 静态化页面。
     * @Description: 根据传入的页面模板和数据渲染页面并输出静态化的HTML
     * @Author: ghost
     * @Date:2021/6/22
     */
    public String gennerateHtml(String template, Map data) {
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", template);
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            String page = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("template"), data);
            if (StringUtils.isEmpty(page)) {
                return page;
            } else {
                return null;
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

//    ======================================页面发布===================================



    public ResponseResult postCmsPage(String pageId){
        if(StringUtils.isEmpty(pageId)){
            return new ResponseResult(CommonCode.FAIL);
        }
        return savePageToServerPath(pageId)?new ResponseResult(CommonCode.SUCCESS):new ResponseResult(CommonCode.FAIL);
    }
    /**
     * @param pageId 发布页面的pageId
     * @Description: 根据提供的pageId将指定静态化后页面保存至页面配置指定路径下
     * 1、根据pageId查询CmsPage,获取页面的siteId和htmlFileId
     * 2、根据htmlFileId获取静态化页面文件
     * 3、根据siteId获取页面保存的节点物理路径
     * 4、根据路径=节点物理路径+页面物理路径+页面名称拼装静态页面保存路径
     * 5、保存静态化页面至指定路径。
     * @Author: ghost
     * @Date:2021/6/22
     */
    public boolean savePageToServerPath(String pageId) {
        if(StringUtils.isEmpty(pageId)){
            return false;
        }
        CmsPage cmsPage = this.findCmsPageById(pageId);
        if(cmsPage==null
                ||StringUtils.isEmpty(cmsPage.getHtmlFileId())
                ||StringUtils.isEmpty(cmsPage.getSiteId())
                ||StringUtils.isEmpty(cmsPage.getPagePhysicalPath())
                ||StringUtils.isEmpty(cmsPage.getPageName())){
            return false;
        }

        String htmlFileId = cmsPage.getHtmlFileId();
        String siteId = cmsPage.getSiteId();
        String sitePhysicalPath = this.findSiteBySiteId(siteId);

        if(StringUtils.isEmpty(sitePhysicalPath)){
            return false;
        }
        String pagePath = sitePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();

        String content=cmsTemplateService.getTemplateFileByTemplateFileId(htmlFileId);
        Map data=getModelByPageId(pageId);
        if(StringUtils.isEmpty(content)||CollectionUtils.isEmpty(data)){
            return false;
        }
        String html=gennerateHtml(content,data);

        if(StringUtils.isEmpty(html)){
            return false;
        }

        InputStream inputStream= IOUtils.toInputStream(html, StandardCharsets.UTF_8);
        if (inputStream == null) {
            LOGGER.error("getFileById() return inputStream is null,getHtmlFileId:{}", htmlFileId);
            return false;
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
        return true;
    }

    /**
     * @param pageId 发布页面的pageId
     * @Description: 根据pageId查询CmsPage, 获取页面的siteId和htmlFileId
     * @Author: ghost
     * @Date:2021/6/22
     */
    public CmsPage findCmsPageById(String pageId) {

        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        return optional.orElse(null);
    }

    /**
     * @param siteId 发布页面所属站点Id
     * @Description: 根据siteId获取页面保存的节点物理路径。
     * @Author: ghost
     * @Date:2021/6/22
     */
    private String findSiteBySiteId(String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        return optional.map(CmsSite::getSitePhysicalPath).orElse(null);
    }
}

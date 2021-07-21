package com.xuecheng.manage_cms.service;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
    RestTemplate restTemplate;

    @Resource
    CmsTemplateService templateService;




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
    public CmsPageResult savePage(CmsPage cmsPage) {
//        参数校验
        if (cmsPage == null) {
            return new CmsPageResult(CmsCode.CMS_PAGE_NOT_EXISTS, null);
        }
        if (StringUtils.isEmpty(cmsPage.getPageId())) {
            //页面唯一性校验
            CmsPage currentPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),
                    cmsPage.getSiteId(), cmsPage.getPageWebPath());
            if (currentPage != null) {
                return new CmsPageResult(CmsCode.CMS_ADDPAGE_EXISTSNAME, cmsPage);
            }
            return (cmsPageRepository.save(cmsPage) == null) ? new CmsPageResult(CommonCode.FAIL, cmsPage) : new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        CmsPage editCmspage= cmsPageRepository.findById(cmsPage.getPageId()).orElse(null);

        if(editCmspage==null){
            return new CmsPageResult(CommonCode.FAIL, cmsPage);
        }
        BeanUtils.copyProperties(cmsPage,editCmspage);
        cmsPageRepository.save(editCmspage);
        return(cmsPageRepository.save(cmsPage) == null) ? new CmsPageResult(CommonCode.FAIL, editCmspage) : new CmsPageResult(CommonCode.SUCCESS, editCmspage);
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
        CmsPage cmsPage=cmsPageRepository.findById(pageId).orElse(null);

        if(cmsPage==null||StringUtils.isEmpty(cmsPage.getDataUrl())||StringUtils.isEmpty(cmsPage.getHtmlFileId())){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        Map model = getModelByDataUrl(cmsPage.getDataUrl());

        if (CollectionUtils.isEmpty(model)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        String template = templateService.getTemplateFileByTemplateFileId(cmsPage.getHtmlFileId());

        if (template == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String html = gennerateHtml(template, model);
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        return html;
    }

    /**
     * @param dataUrl 页面静态化模型数据的dataURL
     * @return Map : 根据dataUrl获取的页面数据
     * @Description: 获取模型数据
     * @Author: ghost
     * @Date:2021/6/22
     */
    private Map getModelByDataUrl(String dataUrl) {
        if (StringUtils.isEmpty(dataUrl)) {
            return new ConcurrentHashMap();
        }

        ResponseEntity<Map> responseEntity =restTemplate.getForEntity(dataUrl,Map.class);

        Map map=responseEntity.getBody();

        if(CollectionUtils.isEmpty(map)){
            return new ConcurrentHashMap();
        }
        return map;
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
            if (!StringUtils.isEmpty(page)) {
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

    /**@Description: 根据提供的pageId将指定静态化后页面保存至页面配置指定路径下
     * 1、根据pageId查询CmsPage,获取页面的siteId和htmlFileId
     * 2、根据htmlFileId获取静态化页面文件
     * 3、根据siteId获取页面保存的节点物理路径
     * 4、根据路径=节点物理路径+页面物理路径+页面名称拼装静态页面保存路径
     * 5、保存静态化页面至指定路径。
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

        Map data=getModelByDataUrl(cmsPage.getDataUrl());

        if(StringUtils.isEmpty(content)||CollectionUtils.isEmpty(data)){
            return false;
        }
        String html=gennerateHtml(content,data);

        return uploadePageToServer(html,pageServerPath);
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
    /**
     *
     * @Description:根据指定的pagePath将page上传到服务器
     * @param page 发布页面
     * @param pagePath 页面发布路径
     * @Author: ghost
     * @Date:2021/6/22
     */
    public boolean uploadePageToServer(String page,String pagePath){

        if(StringUtils.isEmpty(page)){
            return false;
        }

        InputStream inputStream= IOUtils.toInputStream(page, StandardCharsets.UTF_8);
        if (inputStream == null) {
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
    public CmsPage findCmsPageById(String id) {
        return cmsPageRepository.findById(id).orElse(null);
    }
}

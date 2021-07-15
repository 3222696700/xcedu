package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.mapper.CmsConfigRepository;
import com.xuecheng.manage_cms.mapper.CmsPageRepository;
import com.xuecheng.manage_cms.mapper.CmsSiteRepository;
import com.xuecheng.manage_cms.mapper.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
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
    CmsConfigRepository cmsConfigRepository;

    @Resource
    RestTemplate restTemplate;

    @Resource
    CmsTemplateRepository cmsTemplateRepository;

    @Resource
    GridFsTemplate gridFsTemplate;

    @Resource
    GridFSBucket gridFSBucket;

    @Resource
    RabbitTemplate rabbitTemplate;



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
     * @param cmsPage             新增页面信息
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
        }
        else {
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }

        return new CmsPageResult(CommonCode.FAIL, null);
    }

    public CmsPage findById(String Id) {

        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(Id);

        return optionalCmsPage.orElse(null);
    }

    /**
     * @param id    更新CmsPage对象id
     * @param cmsPage 更新CmsPage对象信息
     * @return CmsPageResult : 统一数据返回对象。
     * @Description: 更新指定Id的CmsPage对象
     * @Author: ghost
     * @Date:2021/6/22
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage oldCmsPage = findById(id);

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
     * @param id    删除CmsPage对象id
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

    /**
     * @param id    查询的CmsConfig对象id
     * @return CmsConfig : 返回指定Id的CmsConfig对象。
     * @Description: 查询指定id的CmsConfig对象
     * @Author: ghost
     * @Date:2021/6/22
     */
    public CmsConfig getCmsConfigById(String id){

        Optional<CmsConfig> optional=cmsConfigRepository.findById(id);
        if(optional.isPresent()){
           return  optional.get();
        }
        return null;
    }

    /**
     * @param pageId    静态化页面的id
     * @return String : 静态化页面。
     * @Description: 页面静态化
     * 1、getModelByPageId(pageId)--根据pageId获取CmsPage中的DataUrl,请求DataUrl获取页面的CmsConfig(页面数据)。
     * 2、getTemplateBypageId(pageId)--根据pageId获取CmsTemplate对象的TemplateFileId,通过GriFS提供的API将对相应TemplateFileId的MongoDB文件读取。
     * 3、将页面数据和页面文件（图片等）进行渲染，输出静态HTML。
     * @Author: ghost
     * @Date:2021/6/22
     */

    public String getPageHtml(String pageId){

        Map model=this.getModelByPageId(pageId);

        if(CollectionUtils.isEmpty(model)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }


       String template=this.getTemplateBypageId(pageId);

        if(template==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }


        String html=this.gennerateHtml(template,model);

        if(StringUtils.isEmpty(html)){
            return null;
        }

        return html;
    }


    /**
     * @param pageId    静态化页面的id
     * @return Map : 根据pageId获得的PageConfig对象中URL并请求该接口的页面配置返回值Map集合。
     * @Description: 页面静态化
     * @Author: ghost
     * @Date:2021/6/22
     */
    private Map getModelByPageId(String pageId){
       CmsPage cmsPage= this.findById(pageId);

       if(cmsPage==null){
//         根据pageId无法查询到页面时抛出异常。
           ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
       }

       String dataUrl=cmsPage.getDataUrl();

       if (StringUtils.isEmpty(dataUrl)){

//         根据返回页面获取不到dataUrl时，抛出异常。
           ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
       }

//       通过RestTemplate请求远程接口: ../cms/config/getmodel/{id}
        ResponseEntity<Map> responseEntity=restTemplate.getForEntity(dataUrl,Map.class);

       return responseEntity.getBody();
    }


    /**
     * @param pageId    静态化页面的id
     * @return String : 静态化页面。
     * @Description: 根据提供的pageId获取页面对应的templateId,并通过GridFSBucket加载模板文件
     * @Author: ghost
     * @Date:2021/6/22
     */
    private String getTemplateBypageId(String pageId)  {

        CmsPage cmsPage= this.findById(pageId);

        if(cmsPage==null){
//         根据pageId无法查询到页面时抛出异常。
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        }

        String templateId=cmsPage.getTemplateId();

        if (StringUtils.isEmpty(templateId)){

//         根据返回页面获取不到dataUrl时，抛出异常。
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        Optional<CmsTemplate> optional=cmsTemplateRepository.findById(templateId);

        if(optional.isPresent()){
            CmsTemplate cmsTemplate=optional.get();

            String filed=cmsTemplate.getTemplateFileId();

            GridFSFile gridFSFile=gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(filed)));

            if(gridFSFile==null){

                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
            }

            GridFSDownloadStream gridFSDownloadStream=gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

            GridFsResource gridFsResource=new GridFsResource(gridFSFile,gridFSDownloadStream);

            try {
                String content= IOUtils.toString(gridFsResource.getInputStream(), "utf-8");

                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    /**
     * @param template    静态化页面的模板
     * @param data 静态化页面的数据
     * @return String : 静态化页面。
     * @Description: 根据传入的页面模板和数据渲染页面并输出静态化的HTML
     * @Author: ghost
     * @Date:2021/6/22
     */
    public String gennerateHtml(String template,Map data)  {

        Configuration configuration=new Configuration(Configuration.getVersion());

        StringTemplateLoader stringTemplateLoader=new StringTemplateLoader();

        stringTemplateLoader.putTemplate("template",template);

        configuration.setTemplateLoader(stringTemplateLoader);

        try {
            String page=FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("template"),data);

            if(StringUtils.isEmpty(page)){
                return page;
            }
            else {
                return null;
            }
        }catch (IOException | TemplateException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param pageId    发布页面pageId
     * @return ResponseResult : 统一数据返回对象。
     * @Description: 发布指定pageId的页面
     * @Author: ghost
     * @Date:2021/6/22
     */
    public ResponseResult postPage(String pageId){

        String page=this.getPageHtml(pageId);

        CmsPage cmsPage=this.saveHtml(pageId,page);

        sendMsgToMQ(pageId);

        return new ResponseResult(CommonCode.SUCCESS);
    }


    public CmsPage saveHtml(String pageId,String htmlContent){

       CmsPage cmsPage=this.findById(pageId);

       InputStream inputStream= IOUtils.toInputStream(htmlContent, StandardCharsets.UTF_8);

       ObjectId objectId= gridFsTemplate.store(inputStream,cmsPage.getPageName());

       CmsPage newCmsPage=cmsPageRepository.save(cmsPage);

       return newCmsPage;
    }

    public void sendMsgToMQ(String pageId){

        CmsPage cmsPage=this.findById(pageId);

        if(cmsPage==null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        }

        Map map=new ConcurrentHashMap();

        map.put("pageId",pageId);

        String msg=JSON.toJSONString(map);

        rabbitTemplate.send("ex_routing_cms_postpage",cmsPage.getSiteId(),new Message(msg.getBytes(StandardCharsets.UTF_8),null));
    }


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

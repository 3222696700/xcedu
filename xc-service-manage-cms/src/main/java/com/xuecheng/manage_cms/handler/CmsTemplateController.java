package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.CmsTemplateRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther:ghost
 * @Date:2021/7/15
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {

    @Autowired
    CmsTemplateService cmsTemplateService;


    @Override
    @PostMapping("/base/save")
    public ResponseResult saveTemplate(MultipartFile multipartFile,CmsTemplateRequest cmsTemplateRequest){
       return cmsTemplateService.saveTemplate(multipartFile,cmsTemplateRequest);
    }

    @Override
    public ResponseResult deletTemplateBase(String templateId) {
        return null;
    }

    @Override
    public CmsTemplate queryTemplateBase(String templateId) {
        return null;
    }

    @Override
    public QueryResponseResult<CmsTemplate> queryTemplateBaseList(Integer page, Integer size, CmsTemplateRequest cmsTemplateRequest) {
        return null;
    }

}

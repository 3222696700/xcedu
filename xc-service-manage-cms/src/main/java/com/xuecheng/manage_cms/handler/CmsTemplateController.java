package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.domain.cms.request.CmsTemplateRequest;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/15
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {

    @Resource
    CmsTemplateService cmsTemplateService;

    @Override
    @PostMapping("/save")
    public ResponseResult saveTemplate(@RequestParam("file")MultipartFile multipartFile, CmsTemplateRequest cmsTemplateRequest){
       return cmsTemplateService.saveTemplate(multipartFile,cmsTemplateRequest);
    }
    @Override
    @DeleteMapping("/delete")

    public ResponseResult deletTemplate(String templateId) {
        return cmsTemplateService.deletTemplateBase(templateId);
    }

}

package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.CmsTemplateRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther:ghost
 * @Date:2021/7/16
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value = "模板管理")
public interface CmsTemplateControllerApi {

        ResponseResult saveTemplate(MultipartFile multipartFile,CmsTemplateRequest cmsTemplateRequest);

        ResponseResult deletTemplateBase(String templateId);

        CmsTemplate queryTemplateBase(String templateId);

        QueryResponseResult<CmsTemplate> queryTemplateBaseList(Integer page,Integer size,CmsTemplateRequest cmsTemplateRequest);



}

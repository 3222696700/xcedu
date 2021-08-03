package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.CmsTemplateRequest;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther:ghost
 * @Date:2021/7/16
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value = "页面模板管理接口")
public interface CmsTemplateControllerApi {

        @ApiOperation("保存模板文件和模板信息")
        CmsTemplate saveTemplate(MultipartFile multipartFile, CmsTemplateRequest cmsTemplateRequest);

        @ApiOperation("删除模板文件和模板信息")
        ResponseResult deletTemplate(String templateId);
}

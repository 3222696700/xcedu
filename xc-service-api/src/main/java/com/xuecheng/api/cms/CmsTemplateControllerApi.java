package com.xuecheng.api.cms;

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
@Api(value = "模板管理")
public interface CmsTemplateControllerApi {

        @ApiOperation("保存模板文件和模板信息")
        ResponseResult saveTemplate(MultipartFile multipartFile,CmsTemplateRequest cmsTemplateRequest);

        @ApiOperation("删除模板文件和模板信息")
        ResponseResult deletTemplate(String templateId);

        // todo:页面管理的查询和配置暂不开发，前端还没开发出来。
//        @ApiOperation("根据templateId查询模板信息")
//        CmsTemplate queryTemplateByTemplateId(String templateId);
//
//        @ApiOperation("分页查询模板基本信息")
//        QueryResponseResult<CmsTemplate> queryTemplateBaseList(Integer page,Integer size,CmsTemplateRequest cmsTemplateRequest);



}

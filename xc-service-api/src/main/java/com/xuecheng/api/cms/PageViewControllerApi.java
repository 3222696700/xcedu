package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.course.response.CommonPublishResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:ghost
 * @Date:2021/7/31
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value="页面视图接口")
public interface PageViewControllerApi {
    @ApiOperation("页面预览")
    public CommonPublishResponseResult preview(String pageId);

    @ApiOperation("页面发布")
//  发布页面
    ResponseResult postPage(String pageId);
}

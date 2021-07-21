package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:ghost
 * @Date:2021/7/3
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value="模型数据管理接口")
public interface CmsConfigControllerApi {
    @ApiOperation("根据id获取CmsPage配置信息")
    CmsConfig getCmsConfigById(String id);
}

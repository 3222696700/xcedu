package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsSite;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value = "站点管理接口")
public interface CmsSiteControllerApi {

    @ApiOperation("根据id获取站点")
    CmsSite findCmsSiteBySiteId(String siteId);
}

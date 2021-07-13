package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:ghost
 * @Date:2021/7/11
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value="cms数据字典接口")
public interface SysDictionaryControllerApi {

    @ApiOperation("根据id获取数据字典信息")
    public SysDictionary getModel(String dType);
}

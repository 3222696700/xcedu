package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:http://www.gzcb.com
 * @Date:2021/6/12
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value = "CMS页面管理接口")
public interface CmsPageControllerApi {

    //页面查询
    @ApiOperation("分页查询页面列表")
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    //    保存页面
    @ApiOperation("保存页面（页面存在则更新，不存在则新增）")
    ResponseResult add(CmsPage cmsPage);

    @ApiOperation("根据ID查询页面")
    CmsPage findById(String Id);

    @ApiOperation("删除页面")
//  删除页面
    ResponseResult delete(String pageId);

    @ApiOperation("页面预览")
    public void preview(String pageId);

    @ApiOperation("页面发布")
//  发布页面
    ResponseResult postPage(String pageId);



}

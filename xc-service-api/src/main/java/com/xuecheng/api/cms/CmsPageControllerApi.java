package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:http://www.gzcb.com
 * @Date:2021/6/12
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value = "CMS页面管理接口", description = "提供CMS页面的增删改查功能")
public interface CmsPageControllerApi {

    //页面查询
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

//    页面更新
//    public QueryResponseResult updatePage();

    //    新增页面
    @ApiOperation("新增页面")
    public ResponseResult add(CmsPage cmsPage);

    @ApiOperation("根据ID查询页面")
   public CmsPage findById(String Id);

    @ApiOperation("更新页面")
//  更新页面
    public ResponseResult  update(String Id,CmsPage cmsPage);


}

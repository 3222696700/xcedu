package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Auther:
 * @Date:2021/6/12
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */

@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Resource
    CmsPageService cmsPageService;

    @GetMapping(value = "/list/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {

        return cmsPageService.findList(page, size, queryPageRequest);
    }

    @GetMapping(value = "/get/{id}")
    @Override
    public CmsPageResult findById(@PathVariable("id") String Id) {
        CmsPage cmsPage=cmsPageService.findCmsPageById(Id);
        return     cmsPage  ==null?new CmsPageResult(CommonCode.FAIL,null):new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }

    @PostMapping(value = "/save")
    @Override
    public CmsPageResult savePage(@RequestBody CmsPage cmsPage) {
        return cmsPageService.savePage(cmsPage);
    }

    @DeleteMapping("/del/{id}")
    @Override
    public ResponseResult delete(@PathVariable("id") String id) {
        return cmsPageService.delete(id);
    }





}

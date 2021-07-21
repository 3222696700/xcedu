package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Auther:
 * @Date:2021/6/12
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */

@RestController
@RequestMapping("/cms/page")
public class CmsPageController extends BaseController implements CmsPageControllerApi {

    @Resource
    CmsPageService cmsPageService;

    @GetMapping(value = "/list/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {

        return cmsPageService.findList(page, size, queryPageRequest);
    }

    @GetMapping(value = "/get/{id}")
    @Override
    public CmsPage findById(@PathVariable("id") String Id) {

        return cmsPageService.findCmsPageById(Id);
    }

    @PostMapping(value = "/save")
    @Override
    public ResponseResult add(@RequestBody CmsPage cmsPage) {
        return cmsPageService.savePage(cmsPage);
    }

    @DeleteMapping("/del/{id}")
    @Override
    public ResponseResult delete(@PathVariable("id") String id) {
        return cmsPageService.delete(id);
    }


    @RequestMapping(value={"/preview/{id}"},method = {RequestMethod.GET})
    @Override
    public void preview(@PathVariable("id") String id)  {
        String previewPage=cmsPageService.getPageHtml(id);
        if(StringUtils.isEmpty(previewPage)){
            try {
                ServletOutputStream servletOutputStream = response.getOutputStream();
                servletOutputStream.write(previewPage.getBytes(StandardCharsets.UTF_8));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    @PostMapping("/post/{pageId}")
    @Override
    public ResponseResult postPage(@PathVariable("pageId") String pageId) {
        return cmsPageService.postCmsPage(pageId);
    }

}

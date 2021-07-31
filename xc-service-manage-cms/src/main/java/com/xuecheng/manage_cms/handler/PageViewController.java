package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.PageViewControllerApi;
import com.xuecheng.framework.domain.course.response.CommonPublishResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageViewService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/31
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@RestController("/cms/page")
public class PageViewController  implements PageViewControllerApi {
    @Resource
    PageViewService pageViewService;

    @GetMapping(value={"/view/{id}"})
    @Override
    public CommonPublishResponseResult preview(@PathVariable("id") String id)  {
        String previewPage=pageViewService.getPageHtml(id);
        if(StringUtils.isEmpty(previewPage)){
//            try {
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//                response.setHeader("Content-Type","text/html;charset=UTF-8");
//                servletOutputStream.write(previewPage.getBytes(StandardCharsets.UTF_8));
//            }catch (IOException e){
//                e.printStackTrace();
//            }
        }
        return null;
    }
    @PostMapping("/post/{id}")
    @Override
    public ResponseResult postPage(@PathVariable("id") String pageId) {
        return pageViewService.postCmsPage(pageId);
    }
}

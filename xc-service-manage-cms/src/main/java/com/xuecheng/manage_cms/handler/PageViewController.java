package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.PageViewControllerApi;
import com.xuecheng.framework.domain.cms.response.CmsPagePostResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.PageViewService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Auther:ghost
 * @Date:2021/7/31
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@Controller
@RequestMapping("/cms/page")
public class PageViewController extends BaseController implements PageViewControllerApi {
    @Resource
    PageViewService pageViewService;

    @RequestMapping(value={"/view/{id}"},method= RequestMethod.GET)
    @Override
    public void preview(@PathVariable("id") String id)  {
        String previewPage=pageViewService.previewPage(id);
        if(!StringUtils.isEmpty(previewPage)){
            try {
                ServletOutputStream servletOutputStream = response.getOutputStream();
                response.setHeader("Content-Type","text/html;charset=UTF-8");
                servletOutputStream.write(previewPage.getBytes(StandardCharsets.UTF_8));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/post/{id}")
    @Override
    public CmsPagePostResult postPage(@PathVariable("id") String pageId) {
        return pageViewService.postCmsPage(pageId);
    }
}

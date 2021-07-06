package com.xuecheng.manage_cms.handler;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Auther:ghost
 * @Date:2021/7/6
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@Controller
@RequestMapping("/cms")
public class CmsPreviewController extends BaseController {


    @Autowired
    CmsPageService cmsPageService;

    @RequestMapping(value={"/preview/{id}"},method = {RequestMethod.GET})
    public void preview(@PathVariable("id") String id) throws IOException {
        String previewPage=cmsPageService.getPageHtml(id);

        if(StringUtils.isEmpty(previewPage)){
                ServletOutputStream servletOutputStream=response.getOutputStream();
                servletOutputStream.write(previewPage.getBytes(StandardCharsets.UTF_8));
        }
    }
}

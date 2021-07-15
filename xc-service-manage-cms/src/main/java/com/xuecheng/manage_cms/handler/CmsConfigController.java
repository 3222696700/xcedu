package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther:ghost
 * @Date:2021/7/4
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@RestController
@RequestMapping(value = {"/cms/config"})
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    CmsPageService cmsPageService;


    @GetMapping(value = {"/getmodel/{id}"})
    @Override
    public CmsConfig getModel(@PathVariable("id") String id) {
        return cmsPageService.getCmsConfigById(id);
    }
}

package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */

@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Resource
    CmsConfigService cmsConfigService;

    @GetMapping("/{id}")
    @Override
    public CmsConfig getCmsConfigById(@PathVariable("id") String id){
        return cmsConfigService.getCmsConfigById(id);
    }
}

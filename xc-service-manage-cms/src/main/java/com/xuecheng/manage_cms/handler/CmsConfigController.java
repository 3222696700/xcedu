package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public ResponseResult saveCmsConfig(@RequestBody CmsConfig cmsConfig){
        return cmsConfigService.saveCmsConfig(cmsConfig);
    }
}

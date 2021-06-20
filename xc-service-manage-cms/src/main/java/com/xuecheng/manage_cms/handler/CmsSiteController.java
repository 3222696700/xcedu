package com.xuecheng.manage_cms.handler;

import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsSiteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/6/20
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/cms/sit")
public class CmsSiteController {

    @Resource
    CmsSiteService cmsSiteService;

    @GetMapping("/drop")
    public QueryResponseResult listSite(){
        return cmsSiteService.findAll();
    }
}

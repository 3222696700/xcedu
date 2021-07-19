package com.xuecheng.manage_cms.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.service.CmsConfigService;
import com.xuecheng.manage_cms.service.CmsPageService;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/7/18
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@Controller
@RequestMapping("/cms/")
public class TestHandler {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    CmsPageService cmsPageService;

    @Autowired
    CmsTemplateService cmsTemplateService;

    @Autowired
    CmsConfigService cmsConfigService;

    @RequestMapping(value ="/test",method = RequestMethod.GET)
    public String testFreemarker(Map<String,Object> map)  {

            CmsConfig cmsConfig = cmsConfigService.getCmsConfigById("60e3c144ff2b400f7b02c82b");

            Map data = JSONObject.parseObject(JSON.toJSONString(cmsConfig));

            map.putAll(data);

            return "banner";
    }
}

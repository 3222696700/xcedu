package com.xuecheng.manage_cms.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.manage_cms.service.CmsConfigService;
import com.xuecheng.manage_cms.service.CmsPageService;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/7/18
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@Controller
@RequestMapping("/cms")
public class TestHandler {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    CmsPageService cmsPageService;

    @Autowired
    CmsTemplateService cmsTemplateService;

    @Autowired
    CmsConfigService cmsConfigService;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value ="/test",method = RequestMethod.GET)
    public String testFreemarker(Map<String,Object> map)  {

            CourseView courseView=restTemplate.getForObject("http://localhost:31200/course/view/4028e581617f945f01617f9dabc40000",CourseView.class);

            Map data = JSONObject.parseObject(JSON.toJSONString(courseView));

            map.putAll(data);

            return "course";
    }
}

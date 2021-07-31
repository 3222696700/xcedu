package com.xuecheng.manage_course.handler;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther:ghost
 * @Date:2021/7/22
 * @Description:com.xuecheng.manage_course.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/test")
public class TestControlelr {
    @Autowired
    RestTemplate restTemplate;

    public static final String CMS_MANAGE_SERVICE_URL="http://XC-SERVICE-MANAGE-CMS";

    @Value("${server.port}")
    private String port;

    @GetMapping("/get")
    public String  findCmsPageById(){

       CmsPage cmsPage=restTemplate.getForObject("http://XC-SERVICE-MANAGE-CMS/cms/page/get/60e3bb3d6479f42bd0934514",CmsPage.class);
       return port+cmsPage.toString();
    }
}

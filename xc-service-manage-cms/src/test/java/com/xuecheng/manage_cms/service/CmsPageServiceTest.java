package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuecheng.framework.domain.course.ext.CourseView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/7/4
 * @Description:com.xuecheng.manage_cms.freemarkertest
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageServiceTest {

    @Autowired
   CmsPageService cmsPageService;

    @Autowired
    CmsTemplateService cmsTemplateService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Test
    public void testGetPageHtml() throws FileNotFoundException {
        testGridFS();
        String template= cmsTemplateService.getTemplateFileByTemplateFileId("60f2eca30272ab11b02b6bc2");

        CourseView courseView=restTemplate.getForObject("http://127.0.0.1:31200/course/courseview/4028e581617f945f01617f9dabc40000",CourseView.class);

        Map map=JSONObject.parseObject(JSON.toJSONString(courseView));

        String html=cmsPageService.gennerateHtml(template,map);

        System.out.println(html);

    }

    public String testGridFS() throws FileNotFoundException {
        FileInputStream fileInputStream=new FileInputStream(new File("E:\\course.ftl"));
       return gridFsTemplate.store(fileInputStream, "course").toHexString();

    }


}

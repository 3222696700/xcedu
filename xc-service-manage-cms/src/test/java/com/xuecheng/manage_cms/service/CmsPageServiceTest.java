package com.xuecheng.manage_cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testGetPageHtml(){

        String page=cmsPageService.getPageHtml("60debeeb6479f41fb45f4df8");

        System.out.println(page);




    }


}

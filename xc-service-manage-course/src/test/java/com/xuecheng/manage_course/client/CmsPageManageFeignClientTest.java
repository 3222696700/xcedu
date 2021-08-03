package com.xuecheng.manage_course.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther:ghost
 * @Date:2021/7/22
 * @Description:com.xuecheng.manage_course.client
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageManageFeignClientTest {

    @Autowired
    CmsPageManageFeignClient cmsPageManageFeignClient;


    @Test
    public void testFindCmsPageByPageId(){
//        CmsPage cmsPage=cmsPageManageFeignClient.findCmsPageByPageId("60e3bb3d6479f42bd0934514");
//        System.out.println(cmsPage);
    }
    @Test
    public void testPreView(){
        cmsPageManageFeignClient.preview("60e3bb3d6479f42bd0934514");
    }


}

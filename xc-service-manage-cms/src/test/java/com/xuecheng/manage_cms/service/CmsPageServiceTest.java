package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    ApplicationContext applicationContext;



    @Test
    public void test(){

        RabbitTemplate rabbitTemplate=applicationContext.getBean(RabbitTemplate.class);
//        PageViewService pageViewService= (PageViewService) applicationContext.getBean("pageViewService");
//
//        CmsPagePostResult cmsPagePostResult=pageViewService.postCmsPage("60e3bb3d6479f42bd0934514");
//
//        System.out.println(JSON.toJSON(cmsPagePostResult));

        Map map=new ConcurrentHashMap<>();
        map.put("pageId", "60e3bb3d6479f42bd0934514");
        String message =JSON.toJSON(map).toString();
        rabbitTemplate.convertAndSend("ex_routing_cms_postpage", "5a751fab6abb5044e0d19ea1", message.getBytes(StandardCharsets.UTF_8));
    }

}

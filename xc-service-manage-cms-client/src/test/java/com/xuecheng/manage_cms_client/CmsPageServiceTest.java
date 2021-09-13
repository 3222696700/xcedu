package com.xuecheng.manage_cms_client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/7/4
 * @Description:com.xuecheng.manage_cms.freemarkertest
 * @version:1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsPageServiceTest {

    @Autowired
    ApplicationContext applicationContext;



    @Test
    public void test(){
        MessageConverter messageConverter=applicationContext.getBean(MessageConverter.class);
        System.out.println(messageConverter.getClass());


        RabbitTemplate rabbitTemplate=applicationContext.getBean(RabbitTemplate.class);
//        PageViewService pageViewService= (PageViewService) applicationContext.getBean("pageViewService");
//
//        CmsPagePostResult cmsPagePostResult=pageViewService.postCmsPage("60e3bb3d6479f42bd0934514");
//
//        System.out.println(JSON.toJSON(cmsPagePostResult));
//        System.out.println(rabbitTemplate);



        Map map=(Map)rabbitTemplate.receiveAndConvert("queue_cms_postpage_01");
    }



}

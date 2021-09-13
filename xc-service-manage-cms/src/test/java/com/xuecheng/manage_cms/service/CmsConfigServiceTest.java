package com.xuecheng.manage_cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther:ghost
 * @Date:2021/7/31
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsConfigServiceTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testFindById(){

    }
}

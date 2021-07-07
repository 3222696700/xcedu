package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.manage_cms_client.service.CmsPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/7/6
 * @Description:com.xuecheng.manage_cms_client.mq
 * @version:1.0
 */
@Component
public class ConsumerPostPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsPageService.class);

    @Autowired
    CmsPageService cmsPageService;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String message){

        Map map=JSON.parseObject(message, Map.class);

        String pageId=(String)map.get("pageId");

        if(cmsPageService.findCmsPageById(pageId)==null){

            LOGGER.error("receiver postPage msg:cmsPage is null，pageId:{}",pageId);

            return;
        }
        cmsPageService.savePageToServerPath(pageId);
    }
}

package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther:ghost
 * @Date:2021/9/11
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@Service
public class CmsPagePostConsumer {

    @Autowired
    PageViewService pageViewService;

    @RabbitListener(queues = {"queue_cms_postpage_01"})
    public void savePageToServerPath(String message){
        String pageId= (String) JSONObject.parseObject(message).get("pageId");
        pageViewService.savePageToServerPath(pageId);
    }
}

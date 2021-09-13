package com.xuecheng.manage_cms_client.service;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/9/11
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@Service
public class CmsPagePostConsumer {

    @Autowired
    CmsPageServiceImpl cmsPageService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues ={"${page.post.queue}"})
    public void savePageToServerPath(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        String str=new String(message.getBody(), StandardCharsets.UTF_8);
        Map map= JSONObject.parseObject(str,Map.class);
        String pageId= (String) map.get("pageId");
        cmsPageService.savePageToServerPath(pageId);
    }
}

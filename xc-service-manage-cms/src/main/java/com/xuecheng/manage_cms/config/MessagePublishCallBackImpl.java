package com.xuecheng.manage_cms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @Auther:ghost
 * @Date:2021/9/12
 * @Description:com.xuecheng.manage_cms.util
 * @version:1.0
 */
@Slf4j
//消息发布确认回调
public class MessagePublishCallBackImpl implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
             log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
        } else {
            log.debug("消息发送到exchange失败,原因: {}", cause);
        }
    }
}

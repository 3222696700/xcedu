package com.xuecheng.manage_cms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Auther:ghost
 * @Date:2021/9/12
 * @Description:com.xuecheng.manage_cms.util
 * @version:1.0
 */
@Slf4j
//消息发送完毕回调方法
public class PublicReturnCallBackImpl implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String correlationId = message.getMessageProperties().getCorrelationIdString();
        log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
    }
}

package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:ghost
 * @Date:2021/7/6
 * @Description:com.xuecheng.manage_cms_client
 * @version:1.0
 */
@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_CMS_POSTPAGE="queue_cms_postpage";
    public static final String EX_ROUTING_CMS_POSTPAGE="ex_routing_cms_postpage";

    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;

    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;


    @Bean(EX_ROUTING_CMS_POSTPAGE)
   public Exchange EXCHANGE_TOPICS_INFORM(){
       return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
   }

   @Bean(QUEUE_CMS_POSTPAGE)
   public Queue QUEUE_CMS_POSTPAGE(){
       return new Queue(queue_cms_postpage_name);
   }

   public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_CMS_POSTPAGE) Queue queue,@Qualifier(EX_ROUTING_CMS_POSTPAGE)Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
   }

}
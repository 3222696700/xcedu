package com.xuecheng.template.mapper;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther:ghost
 * @Date:2021/7/8
 * @Description:com.xuecheng.template.mapper
 * @version:1.0
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {


}

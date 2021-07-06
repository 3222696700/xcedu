package com.xuecheng.manage_cms.mapper;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther:ghost
 * @Date:2021/7/4
 * @Description:com.xuecheng.manage_cms.mapper
 * @version:1.0
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}

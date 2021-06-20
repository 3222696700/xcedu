package com.xuecheng.manage_cms.mapper;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther:ghost
 * @Date:2021/6/20
 * @Description:com.xuecheng.manage_cms.mapper
 * @version:1.0
 */

public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}

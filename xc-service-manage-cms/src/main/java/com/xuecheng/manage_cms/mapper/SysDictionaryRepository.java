package com.xuecheng.manage_cms.mapper;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther:ghost
 * @Date:2021/7/11
 * @Description:com.xuecheng.manage_cms.mapper
 * @version:1.0
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    SysDictionary findSysDictionaryBydType(String dType);
}

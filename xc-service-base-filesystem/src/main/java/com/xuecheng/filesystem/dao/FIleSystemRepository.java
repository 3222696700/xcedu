package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther:ghost
 * @Date:2021/7/13
 * @Description:com.xuecheng.filesystem.dao
 * @version:1.0
 */
public interface FIleSystemRepository extends MongoRepository<FileSystem,String> {
}

package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.mapper.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther:ghost
 * @Date:2021/7/12
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@Service
public class SysDictionaryService  {


    @Autowired
    SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary findSysDictionaryByDType(String dType){
        return sysDictionaryRepository.findSysDictionaryBydType(dType).orElse(null);
    }
}

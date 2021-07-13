package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther:ghost
 * @Date:2021/7/12
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysDictionaryServiceTest {
    @Autowired
    SysDictionaryService sysDictionaryService;

    @Test
    public void testFindSysDictionaryBydType(){
        SysDictionary sysDictionary=sysDictionaryService.findSysDictionaryByDType("302");

        System.out.println(sysDictionary);
    }
}

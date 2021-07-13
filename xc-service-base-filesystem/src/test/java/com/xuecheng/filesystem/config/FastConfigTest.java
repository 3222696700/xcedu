package com.xuecheng.filesystem.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther:ghost
 * @Date:2021/7/13
 * @Description:com.xuecheng.filesystem.config
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FastConfigTest {


    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testFfastConfig(){
       FastFSConfig fastFSConfig= applicationContext.getBean(FastFSConfig.class);
       System.out.println(fastFSConfig);
    }


}

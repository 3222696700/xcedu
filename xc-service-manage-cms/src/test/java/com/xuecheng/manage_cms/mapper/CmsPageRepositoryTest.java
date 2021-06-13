package com.xuecheng.manage_cms.mapper;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/6/14
 * @Description:com.xuecheng.manage_cms.mapper
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

//    分页查询页面
    @Test
    public void testFindAllByPage()
    {
        Page page=cmsPageRepository.findAll(PageRequest.of(0,10));
        System.out.println(page);
    }



//    查询所有页面
    @Test
    public void testFindAll()
    {
       List<CmsPage> list= cmsPageRepository.findAll();
       System.out.println(list.size());
    }
}

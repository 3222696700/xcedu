package com.xuecheng.manage_cms.mapper;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

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
    public void testFindAllByPage() {

        Page page = cmsPageRepository.findAll(PageRequest.of(0, 10));

        System.out.println(page);
    }

    //    查询所有页面
    @Test
    public void testFindAll() {
        List<CmsPage> list = cmsPageRepository.findAll();
        System.out.println(list.size());
    }

//    修改页面信息
    @Test
    public void testUpdate() {
        Optional<CmsPage> optional = cmsPageRepository.findById("");

        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();

            cmsPage.setPageName("test01");

            CmsPage cmsPage1=cmsPageRepository.save(cmsPage);

            System.out.println(cmsPage1);
        }

    }

//    删除页面




    //    根据给定条件查询所有页面
    @Test
    public void testFindAllByCondition() {
//        1、设置分页参数
        int page=1;

        int size=10;

        Pageable pageable=PageRequest.of(page,size);


//      2、封装条件查询对象
        CmsPage cmsPage=new CmsPage();

        cmsPage.setPageAliase("课程");

        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");

        cmsPage.setPageId("5ad94ba368db5243ec846e91");


//      3、设置查询条件匹配器
        ExampleMatcher exampleMatcher=ExampleMatcher.matching()
                                                    .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains())
                                                    .withMatcher("siteId",ExampleMatcher.GenericPropertyMatchers.exact())
                                                    .withMatcher("pageId",ExampleMatcher.GenericPropertyMatchers.exact());


//        设置查询查询条件
        Example<CmsPage> example=Example.of(cmsPage,exampleMatcher);

//      4、根据条件查询符合条件页面
        Page<CmsPage> pages = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> list=pages.getContent();
        System.out.println(list);
    }

}

package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/12
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;


    @Test
    public void testGetCategoryWithTree(){
       List<CategoryNode> list= categoryService.getCategoryWithTree();
       System.out.println(list);
    }
}

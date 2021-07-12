package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/12
 * @Description:com.xuecheng.manage_course.dao
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryMapperTest {
    @Resource
    CategoryMapper categoryMapper;

    @Test
    public void testFindAllCategory(){
        List<Category> list= categoryMapper.findAllCategory();
        System.out.println(list);
    }

}

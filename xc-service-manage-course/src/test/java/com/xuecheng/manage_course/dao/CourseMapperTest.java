package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther:ghost
 * @Date:2021/7/9
 * @Description:com.xuecheng.manage_course.dao
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseMapperTest {
    @Autowired
    CourseMapper courseMapper;

    @Test
    public void testFindCourseBaseById(){
        courseMapper.findCourseBaseById("4028e581617f945f01617f9dabc40000");
    }

    @Test
    public void testFindAllCourseBase(){
       PageHelper.startPage(1,10);
       Page<CourseBase> courseBasePage=courseMapper.findAllCourseBase();


    }



}

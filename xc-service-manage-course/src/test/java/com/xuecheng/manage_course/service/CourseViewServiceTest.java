package com.xuecheng.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.course.ext.CourseView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther:ghost
 * @Date:2021/7/26
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseViewServiceTest {
    @Autowired
    CourseViewService courseViewService;

    @Test
    public void testGetCourseViewByCourseid(){
        CourseView courseView=courseViewService.getCourseViewByCourseid("4028e581617f945f01617f9dabc40000");
        System.out.println(JSON.toJSON(courseView));
    }
}

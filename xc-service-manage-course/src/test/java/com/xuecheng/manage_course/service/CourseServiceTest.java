package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther:ghost
 * @Date:2021/7/9
 * @Description:com.xuecheng.service
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseServiceTest {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Test
    public void testSaveCoursePic(){
        CoursePic coursePic=new CoursePic();
        coursePic.setCourseid("950df24ee48f11eb91c7d481d7ebcc92");
        coursePic.setPic("group1/M00/00/00/wKgDM2DuqqqAG3cWAAe5iR7fu7g35.jpeg");
        ResponseResult responseResult=courseService.saveCoursePic(coursePic);
        System.out.println(responseResult);
    }
    @Test
    public void testBoolean(){
        CoursePic coursePic=new CoursePic();
        coursePic.setCourseid("950df24ee48f11eb91c7d481d7ebcc92");
        coursePic.setPic("group1/M00/00/00/wKgDM2DuqqqAG3cWAAe5iR7fu7g35.jpeg");
        boolean b= (courseBaseMapper.findCourseBaseById(coursePic.getCourseid())==null);
        System.out.println(b);
    }
}

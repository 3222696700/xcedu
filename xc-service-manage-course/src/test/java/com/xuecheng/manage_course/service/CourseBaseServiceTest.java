package com.xuecheng.manage_course.service;

import com.alibaba.fastjson.JSONObject;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.model.response.ResponseResult;
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
public class CourseBaseServiceTest {

    @Autowired
    CourseBaseService courseBaseService;


    @Test
    public void testSaveCoursePic(){
        CoursePic coursePic=new CoursePic();
        coursePic.setCourseid("950df24ee48f11eb91c7d481d7ebcc92");
        coursePic.setPic("group1/M00/00/00/wKgDM2DuqqqAG3cWAAe5iR7fu7g35.jpeg");
        ResponseResult responseResult= courseBaseService.saveCoursePic(coursePic);
        System.out.println(responseResult);
    }

    @Test
    public void getCourseViewByCourseid(){
       CourseView courseView= courseBaseService.getCourseViewByCourseid("4028e581617f945f01617f9dabc40000");
       System.out.println(JSONObject.toJSON(courseView));
    }

}

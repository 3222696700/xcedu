package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/9
 * @Description:com.xuecheng.service
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseServiceTest {

    @Resource
    CourseService courseService;


    @Test
    public void testGetTeachPlanWithTree() throws IllegalAccessException, InstantiationException {
        List<TeachplanNode> list=courseService.getTeachPlanWithTree("297e7c7c62b888f00162b8a965510001");
        System.out.println(list);
    }

    @Test
    public void testaddTeachPlan(){
        TeachplanNode teachplanNode=new TeachplanNode();

        teachplanNode.setCourseid("");
        teachplanNode.setPname("");
        teachplanNode.setParentid("");

        courseService.addTeachPlan(teachplanNode);
    }
}

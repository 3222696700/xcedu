package com.xuecheng.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/14
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TeachplanServiceTest {
    @Resource
    TeachplanService teachplanService;


    @Test
    public void testGetTeachPlanWithTree()  {
        List<TeachplanNode> list=teachplanService.getTeachPlanWithTree("4028e581617f945f01617f9dabc40000");
        System.out.println(JSON.toJSON(list));
    }

    @Test
    public void testaddTeachPlan(){
        TeachplanNode teachplanNode=new TeachplanNode();

        teachplanNode.setCourseid("297e7c7c62b888f00162b8a965510001");
        teachplanNode.setPname("添加测试");
        teachplanNode.setParentid("");
        teachplanService.saveTeachplan(teachplanNode);
    }
}

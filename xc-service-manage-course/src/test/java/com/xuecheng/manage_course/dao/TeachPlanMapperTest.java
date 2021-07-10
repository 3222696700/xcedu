package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/9
 * @Description:com.xuecheng.service.mapper
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TeachPlanMapperTest {

    @Autowired
    TeachPlanMapper teachPlanMapper;

    @Test
    public void TestSelectTeachPlanListByCourseId(){
       List<Teachplan> list= teachPlanMapper.getTeachPlanListByCourseId("4028e581617f945f01617f9dabc40000");
       System.out.println(list);
    }
}

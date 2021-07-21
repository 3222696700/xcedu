package com.xuecheng.manage_course.mapper;

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
    public void testSelectTeachPlanListByCourseId(){
       List<Teachplan> list= teachPlanMapper.getTeachPlanListByCourseId("4028e581617f945f01617f9dabc40000");
       System.out.println(list);
    }

    @Test
    public void testInsertSelective(){
        Teachplan teachplan=new Teachplan();
        teachplan.setParentid("0");
        teachplan.setGrade("1");
        teachplan.setPname("test001");
        teachplan.setCourseid("297e7c7c62b888f00162b8a965510001");
        teachplan.setPname("单元测试");
        teachplan.setStatus("0");
        teachPlanMapper.insertSelective(teachplan);
    }


}

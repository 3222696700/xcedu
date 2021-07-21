package com.xuecheng.manage_course.mapper;

import com.xuecheng.framework.domain.course.CoursePic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther:ghost
 * @Date:2021/7/14
 * @Description:com.xuecheng.manage_course.dao
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CoursePicMapperTest {
    @Autowired
    CoursePicMapper coursePicMapper;

        CoursePic coursePic=new CoursePic();


    @Test
    public void testSaveCoursePic(){
        coursePic.setCourseid("950df24ee48f11eb91c7d481d7ebcc92");
        coursePic.setPic("group1/M00/00/00/wKgDM2DuqqqAG3cWAAe5iR7fu7g35.jpeg");
        coursePicMapper.saveCoursePic(coursePic);
    }
    @Test
    public void testSelectCoursePicByCourseId(){
        CoursePic id= coursePicMapper.selectCoursePicByCourseId("950df24ee48f11eb91c7d481d7ebcc92");
        System.out.println(id);
    }
    @Test
    public void testUpdateCoursePic(){
        coursePic.setCourseid("950df24ee48f11eb91c7d481d7ebcc92");
        coursePic.setPic("group1/M00/00/00/wKgDM2DuqqqAG3cWAAe5iR7fu7g35.jpeg");
        coursePicMapper.updateCoursePic(coursePic);

    }
}

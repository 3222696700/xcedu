package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.springframework.stereotype.Repository;

/**
 * @Auther:ghost
 * @Date:2021/7/14
 * @Description:com.xuecheng.manage_course.dao
 * @version:1.0
 */
@Repository
public interface CoursePicMapper {
    Integer  saveCoursePic(CoursePic coursePic);

    CoursePic selectCoursePicByCourseId(String courseid);

    Integer  updateCoursePic(CoursePic coursePic);
}

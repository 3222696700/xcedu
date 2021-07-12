package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator.
 */
@Repository
public interface CourseMapper {
   CourseBase findCourseBaseById(String id);

   Page<CourseBase> findAllCourseBase();
}

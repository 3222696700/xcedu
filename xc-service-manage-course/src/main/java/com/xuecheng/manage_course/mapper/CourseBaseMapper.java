package com.xuecheng.manage_course.mapper;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator.
 */
@Repository
public interface CourseBaseMapper {
   CourseBase findCourseBaseById(String id);

   Page<CourseBase> findAllCourseBase();

    int insertSelective(CourseBase courseBase);

    int updateByPrimaryKeySelective(CourseBase courseBase);
}

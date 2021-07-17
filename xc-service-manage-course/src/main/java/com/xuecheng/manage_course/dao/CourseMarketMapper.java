package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseMarket;

/**
 * @Auther:ghost
 * @Date:2021/7/17
 * @Description:com.xuecheng.manage_course.dao
 * @version:1.0
 */
public interface CourseMarketMapper {
    CourseMarket selectCourseMarketByCourseId(String courseid);
}

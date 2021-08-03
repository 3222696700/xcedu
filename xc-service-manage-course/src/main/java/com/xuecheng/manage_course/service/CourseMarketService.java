package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.manage_course.mapper.CourseMarketMapper;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/8/2
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
public class CourseMarketService {
    @Resource
    CourseMarketMapper courseMarketMapper;

    public CourseMarket findCourseMarketByCourseId(String courseId){
        return courseMarketMapper.selectCourseMarketByCourseId(courseId);
    }
}

package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.manage_course.mapper.CourseBaseMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/14
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@Service
public class CourseBaseService {
    @Resource
    CourseBaseMapper courseBaseMapper;

    public CourseBase findCourseBaseByCourseId(String id){
        return courseBaseMapper.findCourseBaseById(id);
    }


    public boolean saveCourseBase(CourseBase courseBase){
        if(courseBase==null){
            return false;
        }
        if(StringUtils.isEmpty(courseBase.getId())){
            //todo:插入前所需的校验工作
           return courseBaseMapper.insertSelective(courseBase)>0;

        }
       return courseBaseMapper.updateByPrimaryKeySelective(courseBase) > 0;
    }


}

package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseMapper;
import com.xuecheng.manage_course.dao.CoursePicMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther:ghost
 * @Date:2021/7/14
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@Service
public class CourseService {
    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CoursePicMapper coursePicMapper;

    public ResponseResult saveCoursePic(CoursePic coursePic){
        if(coursePic==null
                ||StringUtils.isEmpty(coursePic.getCourseid())
                ||StringUtils.isEmpty(coursePic.getPic())
                ||(courseBaseMapper.findCourseBaseById(coursePic.getCourseid()))==null){
            return new ResponseResult(CommonCode.FAIL);
        }

       CoursePic oldCoursePic=coursePicMapper.selectCoursePicByCourseId(coursePic.getCourseid());

       if(oldCoursePic!=null){
            coursePicMapper.updateCoursePic(coursePic);
            return  isSucces(coursePic)? new ResponseResult(CommonCode.SUCCESS):new ResponseResult(CommonCode.FAIL);
       }

       coursePicMapper.saveCoursePic(coursePic);
       return isSucces(coursePic) ? new ResponseResult(CommonCode.SUCCESS):new ResponseResult(CommonCode.FAIL);
    }

    public boolean isSucces(CoursePic coursePic){
        CoursePic updateCoursePic=coursePicMapper.selectCoursePicByCourseId(coursePic.getCourseid());
        if(coursePic==null
                ||StringUtils.isEmpty(coursePic.getCourseid())
                ||StringUtils.isEmpty(coursePic.getPic())){
            return false;
        }
        return coursePic.getPic().equals(updateCoursePic.getPic())&coursePic.getCourseid().equals(updateCoursePic.getCourseid());
    }


    public CoursePic queryCoursePicByCourseid(String courseid){
        if(StringUtils.isEmpty(courseid)||(courseBaseMapper.findCourseBaseById(courseid))==null){
            return null;
        }
        CoursePic coursePic=coursePicMapper.selectCoursePicByCourseId(courseid);

        if(coursePic==null
                ||StringUtils.isEmpty(coursePic.getCourseid())
                ||StringUtils.isEmpty(coursePic.getPic())){
                return null;
        }
        return coursePic;

    }


    public ResponseResult deleteCoursePicByCourseid(String courseid){
        if(StringUtils.isEmpty(courseid)||(courseBaseMapper.findCourseBaseById(courseid))==null){
            return new ResponseResult(CommonCode.FAIL);
        }
        coursePicMapper.deleteCoursePicByCourseid(courseid);
        return coursePicMapper.selectCoursePicByCourseId(courseid)==null?new ResponseResult(CommonCode.SUCCESS):new ResponseResult(CommonCode.FAIL);

    }
}

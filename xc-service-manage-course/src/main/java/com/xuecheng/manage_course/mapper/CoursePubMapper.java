package com.xuecheng.manage_course.mapper;

import com.xuecheng.framework.domain.course.CoursePub;
import org.springframework.stereotype.Repository;

/**
 * @Auther:ghost
 * @Date:2021/8/5
 * @Description:com.xuecheng.manage_course.mapper
 * @version:1.0
 */
@Repository
public interface CoursePubMapper {
    int insertSelective(CoursePub coursePub);

    int updateByPrimaryKeySelective(CoursePub coursePub);

}

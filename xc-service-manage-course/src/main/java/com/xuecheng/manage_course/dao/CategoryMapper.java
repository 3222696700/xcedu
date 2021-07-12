package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Category;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/12
 * @Description:com.xuecheng.manage_course.dao
 * @version:1.0
 */
public interface CategoryMapper {

    List<Category> findAllCategory();
}

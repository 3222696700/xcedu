package com.xuecheng.manage_course.handler;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.manage_course.handler
 * @version:1.0
 */
public class CategoryController  implements CategoryControllerApi {

    @Resource
    CategoryService categoryService;

    @GetMapping("/category/list")
    @Override
    public List<CategoryNode> findAllCategory() {
        return categoryService.getCategoryWithTree();
    }
}

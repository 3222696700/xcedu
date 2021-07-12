package com.xuecheng.manage_course.util;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther:ghost
 * @Date:2021/7/12
 * @Description:com.xuecheng.manage_course.util
 * @version:1.0
 */
public class CategoryNodeLevelUtil {
    public static final String ROOT_PARENT_ID="0";

    public static final String CATEGORY_LEVEL_SEPARATOR="-";

    public static String calculateNextCategoryGrade(CategoryNode categoryNode){
        if(ROOT_PARENT_ID.equals(categoryNode.getParentid())){
            return categoryNode.getId();
        }
        return StringUtils.join(categoryNode.getParentid(),CATEGORY_LEVEL_SEPARATOR,categoryNode.getOrderby());
    }
}

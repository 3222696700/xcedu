package com.xuecheng.framework.utils;

/**
 * @Auther:ghost
 * @Date:2021/7/12
 * @Description:com.xuecheng.framework.utils
 * @version:1.0
 */
public class GradeUtil {
    public static final String ROOT_LEVE="1";

    public static final String ROOT_PARENT_ID="0";

    public static final String CATEGORY_LEVEL_SEPARATOR="-";
    public static String calculateNextGrade(String grade) {
        Integer integer=Integer.parseInt(grade);

        int oldLevel=integer.intValue();

        int newLevel=oldLevel+1;

        return Integer.toString(newLevel);
    }


}

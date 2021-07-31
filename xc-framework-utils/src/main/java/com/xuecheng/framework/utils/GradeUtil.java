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

    public static String calculateNextGrade(String grade) {
        int integer=Integer.parseInt(grade);

        int newLevel= integer +1;

        return Integer.toString(newLevel);
    }


}

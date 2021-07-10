package com.xuecheng.framework.utils;

/**
 * @Auther:ghost
 * @Date:2021/7/8
 * @Description:com.xuecheng.framework.utils
 * @version:1.0
 */
public class LevelUtil {

    public static final String ROOT_LEVE="1";

    public static String caculateLevel(String currentLevel){

        Integer integer=Integer.parseInt(currentLevel);

        int oldLevel=integer.intValue();

        int newLevel=oldLevel+1;

        return Integer.toString(newLevel);
    }
}

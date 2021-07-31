package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;

/**
 * @Auther:ghost
 * @Date:2021/7/26
 * @Description:com.xuecheng.framework.domain.course.response
 * @version:1.0
 */
public class CommonPublishResponseResult extends ResponseResult {
    private String url;
    public CommonPublishResponseResult(ResultCode resultCode,String url){
        super(resultCode);
        this.url=url;
    }
}

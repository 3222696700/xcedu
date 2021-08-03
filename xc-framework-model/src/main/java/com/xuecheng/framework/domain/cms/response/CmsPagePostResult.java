package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;

/**
 * @Auther:ghost
 * @Date:2021/8/2
 * @Description:com.xuecheng.framework.domain.cms.response
 * @version:1.0
 */
@Data
public class CmsPagePostResult extends ResponseResult {
    String url;
    public CmsPagePostResult(ResultCode resultCode,String url){
        super(resultCode);
        this.url=url;
    }
}

package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther:http://www.gzcb.com
 * @Date:2021/6/12
 * @Description:com.xuecheng.framework.domain.cms.request
 * @version:1.0
 */
@Data
public class QueryPageRequest  extends RequestData {

    //接收页面查询的查询条件
    //站点id
    //站点id
    @ApiModelProperty("站点id")
    private String siteId;

    //页面ID
    @ApiModelProperty("页面ID")
    private String pageId;

    //页面名称
    @ApiModelProperty("页面名称")
    private String pageName;

    //别名
    @ApiModelProperty("别名")
    private String pageAliase;
    //模版id
    @ApiModelProperty("模版id")
    private String templateId;
    //....
}

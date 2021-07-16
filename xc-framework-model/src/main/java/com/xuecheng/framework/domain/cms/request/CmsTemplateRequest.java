package com.xuecheng.framework.domain.cms.request;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @Auther:ghost
 * @Date:2021/7/16
 * @Description:com.xuecheng.framework.domain.cms.request
 * @version:1.0
 */
@Data
public class CmsTemplateRequest{
    //站点ID
    //站点ID
    private String siteId;

    //模版ID
    @Id
    private String templateId;

    //模版名称
    private String templateName;

    //模版参数
    private String templateParameter;

    //模版文件Id
    private String templateFileId;
}

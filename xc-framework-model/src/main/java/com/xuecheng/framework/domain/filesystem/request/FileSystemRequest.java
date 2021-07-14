package com.xuecheng.framework.domain.filesystem.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther:ghost
 * @Date:2021/7/13
 * @Description:com.xuecheng.framework.domain.filesystem
 * @version:1.0
 */
@Data
public class FileSystemRequest extends RequestData {
    @ApiModelProperty("站点id")
    private String filetag;

    @ApiModelProperty("站点id")
    private String metadata;

    @ApiModelProperty("站点id")
    private String businesskey;

}

package com.ruoshui.bigdata.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 */
@Data
public class BaseResource {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("资源名称")
    private String name;

	@ApiModelProperty("资源地址")
	private String resource_address;

	@ApiModelProperty("更新时间")
	private String update_time;

    @ApiModelProperty("服务器IP")
    private String serverIp;

    @ApiModelProperty("服务器用户名")
    private String serverUser;

    @ApiModelProperty("服务器密码")
    private String serverPassword;

    @ApiModelProperty("资源类型")
    private String type;


}

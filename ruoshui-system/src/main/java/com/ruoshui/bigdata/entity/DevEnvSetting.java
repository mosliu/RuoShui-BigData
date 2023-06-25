package com.ruoshui.bigdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
public class DevEnvSetting {

    @ApiModelProperty("属性Id")
    private int id;

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("属性值")
    private String propValue;

    @ApiModelProperty("属性描述")
    private String description;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("标记")
    private Boolean flag;

	@ApiModelProperty("上传的URL")
	private Boolean uploadurl;

	@ApiModelProperty("部署的URL")
	private Boolean deployurl;

	@ApiModelProperty("展示的URL")
	private Boolean showurl;

	@ApiModelProperty("下线的URL")
	private Boolean offlineurl;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableField(exist=false)
    private String userName;
}

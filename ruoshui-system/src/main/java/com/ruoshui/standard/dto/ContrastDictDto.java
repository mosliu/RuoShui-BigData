package com.ruoshui.standard.dto;

import com.ruoshui.metadata.validate.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 字典对照信息表 实体DTO
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@ApiModel(value = "字典对照信息表Model")
@Data
public class ContrastDictDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
    @NotBlank(message = "主键ID不能为空", groups = {ValidationGroups.Update.class})
    private String id;
    @ApiModelProperty(value = "字典对照主键")
    private String contrastId;
    @ApiModelProperty(value = "字典编码")
    private String colCode;
    @ApiModelProperty(value = "字典名称")
    private String colName;
    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空", groups = {ValidationGroups.Insert.class, ValidationGroups.Update.class})
    private String status;
    @ApiModelProperty(value = "备注")
    private String remark;
}

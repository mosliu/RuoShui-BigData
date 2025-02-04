package com.ruoshui.standard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 对照表信息表 实体VO
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Data
public class ContrastVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String remark;
    private String sourceId;
    private String sourceName;
    private String tableId;
    private String tableName;
    private String tableComment;
    private String columnId;
    private String columnName;
    private String columnComment;
    private String gbTypeId;
    private String gbTypeCode;
    private String gbTypeName;
    private String bindGbColumn;
}

package com.ruoshui.standard.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典对照信息表 实体VO
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Data
public class ContrastDictVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String remark;
    private String contrastId;
    private String colCode;
    private String colName;
    private String contrastGbId;
    private String contrastGbCode;
    private String contrastGbName;
    private String sourceName;
    private String tableName;
    private String columnName;
    private String gbTypeCode;
    private String gbTypeName;
}

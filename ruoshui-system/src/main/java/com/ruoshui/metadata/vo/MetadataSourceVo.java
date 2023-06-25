package com.ruoshui.metadata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoshui.metadata.dto.DbSchema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 数据源信息表 实体VO
 * </p>
 *
 * @author yuwei
 * @since 2020-03-14
 */
@Data
public class MetadataSourceVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String remark;
    private String dbType;
    private String sourceName;
    private DbSchema dbSchema;
    private String isSync;
}

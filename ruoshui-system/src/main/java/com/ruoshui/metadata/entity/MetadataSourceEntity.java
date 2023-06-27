package com.ruoshui.metadata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoshui.common.core.domain.BaseEntity;
import com.ruoshui.metadata.dto.DbSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 数据源信息表
 * </p>
 *
 * @author yuwei
 * @since 2020-03-14
 */
@Data
@Accessors(chain = true)
@TableName(value = "metadata_source", autoResultMap = true)
public class MetadataSourceEntity  {

    private static final long serialVersionUID=1L;

    /**
     * 数据源类型
     */
    private String dbType;

    /**
     * 数据源名称
     */
    private String sourceName;

    /**
     * 元数据同步（0否，1同步中, 2是）
     */
    private String isSync;

    private String id;

    private String status;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    private String createDept;

    /**
     * 数据源连接信息
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private DbSchema dbSchema;
}

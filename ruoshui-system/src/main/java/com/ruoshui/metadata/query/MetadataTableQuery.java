package com.ruoshui.metadata.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据库表信息表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MetadataTableQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String sourceId;

    private String tableName;
}

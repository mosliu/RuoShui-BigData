package com.ruoshui.standard.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 对照表信息表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContrastQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String sourceName;
    private String tableName;
    private String columnName;
}

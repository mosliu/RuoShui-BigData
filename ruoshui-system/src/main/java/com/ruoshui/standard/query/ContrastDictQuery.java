package com.ruoshui.standard.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典对照信息表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContrastDictQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String contrastId;
    private String colCode;
    private String colName;
}

package com.ruoshui.market.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * api调用日志信息表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiLogQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String apiName;
}

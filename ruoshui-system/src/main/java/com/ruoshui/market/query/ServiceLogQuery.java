package com.ruoshui.market.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 服务集成调用日志表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceLogQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String serviceName;
}
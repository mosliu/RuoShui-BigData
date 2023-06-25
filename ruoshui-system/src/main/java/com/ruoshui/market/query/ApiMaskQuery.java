package com.ruoshui.market.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据API脱敏信息表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiMaskQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String maskName;
}

package com.ruoshui.quality.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据质量监控任务日志信息表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-10-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleLogQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String executeJobId;
    private String ruleTypeId;
}

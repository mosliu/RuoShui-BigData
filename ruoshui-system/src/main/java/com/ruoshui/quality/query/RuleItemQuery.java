package com.ruoshui.quality.query;

import com.ruoshui.core.database.base.BaseQueryParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 规则核查项信息表 查询实体
 * </p>
 *
 * @author yuwei
 * @since 2020-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RuleItemQuery extends BaseQueryParams {

    private static final long serialVersionUID=1L;

    private String ruleTypeId;
}

package com.ruoshui.quality.service;


import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.quality.dto.CheckRuleDto;
import com.ruoshui.quality.entity.CheckRuleEntity;

import java.util.List;

/**
 * <p>
 * 核查规则信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
public interface CheckRuleService extends BaseService<CheckRuleEntity> {

    CheckRuleEntity saveCheckRule(CheckRuleDto checkRule);

    CheckRuleEntity updateCheckRule(CheckRuleDto checkRule);

    CheckRuleEntity getCheckRuleById(String id);

    void deleteCheckRuleById(String id);

    void deleteCheckRuleBatch(List<String> ids);
}

package com.ruoshui.quality.service;


import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.quality.entity.RuleItemEntity;

/**
 * <p>
 * 规则核查项信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-10-15
 */
public interface RuleItemService extends BaseService<RuleItemEntity> {

    RuleItemEntity getRuleItemById(String id);
}

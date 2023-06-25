package com.ruoshui.quality.service;

import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.quality.entity.RuleLevelEntity;

/**
 * <p>
 * 规则级别信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-10-14
 */
public interface RuleLevelService extends BaseService<RuleLevelEntity> {

    RuleLevelEntity getRuleLevelById(String id);
}

package com.ruoshui.quality.service;


import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.quality.entity.RuleTypeEntity;
import com.ruoshui.quality.entity.RuleTypeReportEntity;

import java.util.List;

/**
 * <p>
 * 规则类型信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
public interface RuleTypeService extends BaseService<RuleTypeEntity> {

    RuleTypeEntity getRuleTypeById(String id);

    List<RuleTypeReportEntity> getRuleTypeListForReport();
}

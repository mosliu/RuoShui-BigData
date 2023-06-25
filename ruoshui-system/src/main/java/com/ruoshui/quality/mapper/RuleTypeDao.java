package com.ruoshui.quality.mapper;


import com.ruoshui.core.database.base.BaseDao;
import com.ruoshui.quality.entity.RuleTypeEntity;
import com.ruoshui.quality.entity.RuleTypeReportEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 规则类型信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Mapper
public interface RuleTypeDao extends BaseDao<RuleTypeEntity> {

    List<RuleTypeReportEntity> selectListForReport();
}

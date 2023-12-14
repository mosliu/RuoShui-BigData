package com.ruoshui.quality.service.impl;


import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.quality.entity.RuleTypeEntity;
import com.ruoshui.quality.entity.RuleTypeReportEntity;
import com.ruoshui.quality.mapper.RuleTypeDao;
import com.ruoshui.quality.mapstruct.RuleTypeMapper;
import com.ruoshui.quality.service.RuleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 规则类型信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RuleTypeServiceImpl extends BaseServiceImpl<RuleTypeDao, RuleTypeEntity> implements RuleTypeService {

    @Autowired
    private RuleTypeDao ruleTypeDao;

    @Autowired
    private RuleTypeMapper ruleTypeMapper;

    @Override
    public RuleTypeEntity getRuleTypeById(String id) {
        RuleTypeEntity ruleTypeEntity = super.getById(id);
        return ruleTypeEntity;
    }

    @Override
    public List<RuleTypeReportEntity> getRuleTypeListForReport() {
        List<RuleTypeReportEntity> list = ruleTypeDao.selectListForReport();
        return list;
    }
}

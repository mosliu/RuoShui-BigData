package com.ruoshui.quality.service.impl;

import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.quality.entity.RuleItemEntity;
import com.ruoshui.quality.mapper.RuleItemDao;
import com.ruoshui.quality.mapstruct.RuleItemMapper;
import com.ruoshui.quality.service.RuleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 规则核查项信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-10-15
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RuleItemServiceImpl extends BaseServiceImpl<RuleItemDao, RuleItemEntity> implements RuleItemService {

    @Autowired
    private RuleItemDao ruleItemDao;

    @Autowired
    private RuleItemMapper ruleItemMapper;

    @Override
    public RuleItemEntity getRuleItemById(String id) {
        RuleItemEntity ruleItemEntity = super.getById(id);
        return ruleItemEntity;
    }
}

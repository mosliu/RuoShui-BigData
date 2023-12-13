package com.ruoshui.quality.service.impl;


import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.quality.entity.RuleLevelEntity;
import com.ruoshui.quality.mapper.RuleLevelDao;
import com.ruoshui.quality.mapstruct.RuleLevelMapper;
import com.ruoshui.quality.service.RuleLevelService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 规则级别信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-10-14
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RuleLevelServiceImpl extends BaseServiceImpl<RuleLevelDao, RuleLevelEntity> implements RuleLevelService {

    @Resource
    private RuleLevelDao ruleLevelDao;

    @Resource
    private RuleLevelMapper ruleLevelMapper;

    @Override
    public RuleLevelEntity getRuleLevelById(String id) {
        RuleLevelEntity ruleLevelEntity = super.getById(id);
        return ruleLevelEntity;
    }
}

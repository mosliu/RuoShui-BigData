package com.ruoshui.quality.service.impl;

import com.ruoshui.common.core.redis.RedisCache;
import com.ruoshui.common.database.constants.DbType;
import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.core.database.core.RedisConstant;
import com.ruoshui.quality.dto.*;
import com.ruoshui.quality.entity.CheckRuleEntity;
import com.ruoshui.quality.enums.RuleItem;
import com.ruoshui.quality.mapper.CheckRuleDao;
import com.ruoshui.quality.mapstruct.CheckRuleMapper;
import com.ruoshui.quality.schedule.CheckRuleFactory;
import com.ruoshui.quality.service.CheckRuleService;
import com.ruoshui.standard.entity.DictEntity;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 核查规则信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CheckRuleServiceImpl extends BaseServiceImpl<CheckRuleDao, CheckRuleEntity> implements CheckRuleService {

    @Resource
    private CheckRuleDao checkRuleDao;

    @Resource
    private CheckRuleMapper checkRuleMapper;

    @Resource
    private RedisCache redisService;

    private static String BIND_GB_CODE = "gb_code";
    private static String BIND_GB_NAME = "gb_name";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CheckRuleEntity saveCheckRule(CheckRuleDto checkRuleDto) {
        CheckRuleEntity checkRule = checkRuleMapper.toEntity(checkRuleDto);
        String sql = parseSql(checkRule);
        checkRule.setRuleSql(sql);
        checkRuleDao.insert(checkRule);
        return checkRule;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CheckRuleEntity updateCheckRule(CheckRuleDto checkRuleDto) {
        CheckRuleEntity checkRule = checkRuleMapper.toEntity(checkRuleDto);
        String sql = parseSql(checkRule);
        checkRule.setRuleSql(sql);
        checkRuleDao.updateById(checkRule);
        return checkRule;
    }

    @Override
    public CheckRuleEntity getCheckRuleById(String id) {
        CheckRuleEntity checkRuleEntity = super.getById(id);
        return checkRuleEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCheckRuleById(String id) {
        checkRuleDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCheckRuleBatch(List<String> ids) {
        checkRuleDao.deleteBatchIds(ids);
    }

    private String parseSql(CheckRuleEntity checkRule) {
        RuleConfig ruleConfig = checkRule.getRuleConfig();
        Map<String, Object> map = new HashMap<>();
        RuleItem ruleItem = RuleItem.getRuleItem(ruleConfig.getRuleItemCode());
        switch (ruleItem) {
            case Unique:
            case Integrity:
                break;
            // 一致性参数处理
            case Consistent:
                Consistent consistent = ruleConfig.getConsistent();
                List<DictEntity> dictEntityList = (List<DictEntity>) redisService.hget(RedisConstant.STANDARD_DICT_KEY, consistent.getGbTypeId());
                String collect = dictEntityList.stream().map(s -> {
                    if (BIND_GB_CODE.equals(consistent.getBindGbColumn())) {
                        return "\'" +  s.getGbCode() + "\'";
                    } else {
                        return "\'" +  s.getGbName() + "\'";
                    }
                }).collect(Collectors.joining(","));
                map.put("gb_item", collect);
                break;
            // 关联性参数处理
            case Relevance:
                Relevance relevance = ruleConfig.getRelevance();
                map.put("related_table", relevance.getRelatedTable());
                map.put("related_column", relevance.getRelatedColumn());
                break;
            // 及时性参数处理
            case Timeliness:
                Timeliness timeliness = ruleConfig.getTimeliness();
                map.put("threshold", timeliness.getThreshold());
                break;
            // 准确性参数处理
            case AccuracyLength:
                Accuracy accuracy = ruleConfig.getAccuracy();
                map.put("max_length", accuracy.getMaxLength());
                break;
            default:
                return null;
        }
        DbType dbType = DbType.getDbType(checkRule.getRuleDbType());
        String sql = CheckRuleFactory.getRuleItem(ruleConfig.getRuleItemCode()).parse(dbType, checkRule.getRuleTable(), checkRule.getRuleColumn(), map);
        return sql;
    }
}

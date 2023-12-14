package com.ruoshui.quality.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.quality.entity.CheckReportEntity;
import com.ruoshui.quality.entity.DataReportEntity;
import com.ruoshui.quality.entity.RuleLevelEntity;
import com.ruoshui.quality.entity.RuleTypeEntity;
import com.ruoshui.quality.mapper.CheckReportDao;
import com.ruoshui.quality.mapper.RuleLevelDao;
import com.ruoshui.quality.mapper.RuleTypeDao;
import com.ruoshui.quality.mapstruct.CheckReportMapper;
import com.ruoshui.quality.service.CheckReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 核查报告信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CheckReportServiceImpl extends BaseServiceImpl<CheckReportDao, CheckReportEntity> implements CheckReportService {

    @Autowired
    private CheckReportDao checkReportDao;

    @Autowired
    private CheckReportMapper checkReportMapper;

    @Autowired
    private RuleLevelDao ruleLevelDao;

    @Autowired
    private RuleTypeDao ruleTypeDao;

    @Override
    public CheckReportEntity getCheckReportById(String id) {
        CheckReportEntity checkReportEntity = super.getById(id);
        return checkReportEntity;
    }

    @Override
    public List<DataReportEntity> getReportBySource(String checkDate) {
        List<RuleLevelEntity> ruleLevelList = ruleLevelDao.selectList(Wrappers.emptyWrapper());
        List<DataReportEntity> list = checkReportDao.getReportBySource(checkDate);
        // 补全数据
        List<DataReportEntity> differenceReportList = new ArrayList<>();
        // 补全数据源分组缺失的规则级别数据
        Map<String, List<DataReportEntity>> sourceMap = list.stream().collect(Collectors.groupingBy(DataReportEntity::getRuleSourceId));
        Iterator<Map.Entry<String, List<DataReportEntity>>> sourceIterator = sourceMap.entrySet().iterator();
        while (sourceIterator.hasNext()) {
            Map.Entry<String, List<DataReportEntity>> sourceEntry = sourceIterator.next();
            List<DataReportEntity> entryValue = sourceEntry.getValue();
            DataReportEntity dataReportEntity = entryValue.get(0);
            // 差集 (ruleLevelList - entryValue)
            ruleLevelList.stream().filter(item -> entryValue.stream().map(DataReportEntity::getRuleLevelId).noneMatch(id -> Objects.equals(item.getId(), id)))
                    .forEach(s -> {
                        DataReportEntity report = new DataReportEntity();
                        report.setRuleSourceId(dataReportEntity.getRuleSourceId());
                        report.setRuleSourceName(dataReportEntity.getRuleSourceName());
                        report.setRuleLevelId(s.getId());
                        report.setRuleLevelName(s.getName());
                        report.setCheckErrorCount(0);
                        differenceReportList.add(report);
                    });
        }
        list.addAll(differenceReportList);
        // 排序
        list = list.stream().sorted(Comparator.comparing(DataReportEntity::getRuleSourceId).thenComparing(DataReportEntity::getRuleLevelId)).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<DataReportEntity> getReportByType(String checkDate) {
        List<DataReportEntity> list = checkReportDao.getReportByType(checkDate);
        // 排序
        list = list.stream().sorted(Comparator.comparing(DataReportEntity::getRuleTypeId)).collect(Collectors.toList());
        return list;
    }

    @Override
    public Map<String, Object> getReportDetail(String checkDate) {
        Map<String, Object> map = new HashMap<>();
        List<RuleTypeEntity> ruleTypeList = ruleTypeDao.selectList(Wrappers.emptyWrapper());
        List<DataReportEntity> dataReportList = checkReportDao.getReportDetail(checkDate);
        Map<String, List<DataReportEntity>> listMap = dataReportList.stream().collect(Collectors.groupingBy(DataReportEntity::getRuleTypeCode));
        ruleTypeList.stream().forEach(s -> {
            map.put(s.getCode(), listMap.get(s.getCode()));
        });
        return map;
    }
}

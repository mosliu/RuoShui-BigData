package com.ruoshui.standard.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoshui.core.database.core.DataConstant;
import com.ruoshui.standard.dto.Endpoint;
import com.ruoshui.standard.dto.ManualMappingDto;
import com.ruoshui.standard.entity.ContrastDictEntity;
import com.ruoshui.standard.entity.ContrastEntity;
import com.ruoshui.standard.entity.DictEntity;
import com.ruoshui.standard.mapper.ContrastDao;
import com.ruoshui.standard.mapper.ContrastDictDao;
import com.ruoshui.standard.mapper.DictDao;
import com.ruoshui.standard.mapstruct.ContrastDictMapper;
import com.ruoshui.standard.mapstruct.DictMapper;
import com.ruoshui.standard.service.DictMappingService;
import com.ruoshui.standard.vo.ContrastDictVo;
import com.ruoshui.standard.vo.DictVo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DictMappingServiceImpl implements DictMappingService {

    @Resource
    private ContrastDao contrastDao;

    @Resource
    private ContrastDictDao contrastDictDao;

    @Resource
    private ContrastDictMapper contrastDictMapper;

    @Resource
    private DictDao dictDao;

    @Resource
    private DictMapper dictMapper;

    private static String BIND_GB_CODE = "gb_code";
    private static String BIND_GB_NAME = "gb_name";

    @Override
    public Map<String, Object> getDictMapping(String id) {
        ContrastEntity contrastEntity = contrastDao.selectById(id);
        String contrastId = contrastEntity.getId();
        String gbTypeId = contrastEntity.getGbTypeId();
        QueryWrapper<ContrastDictEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("d.contrast_id", contrastId);
        List<ContrastDictEntity> contrastDictEntityList = contrastDictDao.selectList(queryWrapper);
        List<ContrastDictVo> contrastDictList = contrastDictEntityList.stream().map(contrastDictMapper::toVO).collect(Collectors.toList());
        List<DictEntity> dictEntityList = dictDao.selectList(Wrappers.<DictEntity>lambdaQuery().eq(DictEntity::getTypeId, gbTypeId).eq(DictEntity::getStatus, DataConstant.TrueOrFalse.TRUE.getKey()));
        List<DictVo> dictList = dictEntityList.stream().map(dictMapper::toVO).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>(4);
        String tableName = StrUtil.isBlank(contrastEntity.getTableComment()) ? contrastEntity.getTableName() : contrastEntity.getTableName() + "(" + contrastEntity.getTableComment() + ")";
        String columnName = StrUtil.isBlank(contrastEntity.getColumnComment()) ? contrastEntity.getTableName() : contrastEntity.getColumnName() + "(" + contrastEntity.getColumnComment() + ")";
        long contrastTotal = contrastDictList.stream().count();
        long unContrastTotal = contrastDictList.stream().filter(s -> DataConstant.TrueOrFalse.FALSE.getKey().equals(s.getStatus())).count();
        map.put("title", "数据源: " + contrastEntity.getSourceName() + " 数据表: " + tableName + " 对照字段: " + columnName + " 标准类别编码: " + contrastEntity.getGbTypeCode() + " 标准类别名称: " + contrastEntity.getGbTypeName());
        map.put("description", "总数: " + contrastTotal + " 未对照: " + unContrastTotal + " 已对照: " + (contrastTotal - unContrastTotal));
        map.put("left", contrastDictList);
        map.put("right", dictList);
        return map;
    }

    @Override
    public void dictAutoMapping(String id) {
        ContrastEntity contrastEntity = contrastDao.selectById(id);
        String contrastId = contrastEntity.getId();
        String gbTypeId = contrastEntity.getGbTypeId();
        String bindGbColumn = contrastEntity.getBindGbColumn();
        // 查询未对照数据
        QueryWrapper<ContrastDictEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("d.contrast_id", contrastId);
        queryWrapper.eq("d.status", DataConstant.TrueOrFalse.FALSE.getKey());
        List<ContrastDictEntity> contrastDictEntityList = contrastDictDao.selectList(queryWrapper);
        // 查询标准字典数据
        List<DictEntity> dictEntityList = dictDao.selectList(Wrappers.<DictEntity>lambdaQuery().eq(DictEntity::getTypeId, gbTypeId).eq(DictEntity::getStatus, DataConstant.TrueOrFalse.TRUE.getKey()));
        contrastDictEntityList.stream().forEach(c -> {
            dictEntityList.stream().filter(d -> {
                if (BIND_GB_CODE.equals(bindGbColumn)) {
                    return Objects.equals(c.getColCode(), d.getGbCode());
                } else {
                    return Objects.equals(c.getColName(), d.getGbName());
                }
            }).forEach(s -> {
                // 更新对照结果
                String contrastGbId = s.getId();
                c.setStatus(DataConstant.TrueOrFalse.TRUE.getKey());
                c.setContrastGbId(contrastGbId);
                contrastDictDao.updateById(c);
            });
        });
    }

    @Override
    public void dictManualMapping(ManualMappingDto manualMappingDto) {
        List<Endpoint> endpoints = manualMappingDto.getEndpoints();
        endpoints.stream().forEach(s -> {
            LambdaUpdateWrapper<ContrastDictEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(ContrastDictEntity::getStatus, DataConstant.TrueOrFalse.TRUE.getKey());
            updateWrapper.set(ContrastDictEntity::getContrastGbId, s.getTargetId());
            updateWrapper.eq(ContrastDictEntity::getId, s.getSourceId());
            contrastDictDao.update(null, updateWrapper);
        });
    }

    @Override
    public void dictCancelMapping(String id) {
        LambdaUpdateWrapper<ContrastDictEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ContrastDictEntity::getStatus, DataConstant.TrueOrFalse.FALSE.getKey());
        updateWrapper.set(ContrastDictEntity::getContrastGbId, null);
        updateWrapper.eq(ContrastDictEntity::getId, id);
        contrastDictDao.update(null, updateWrapper);
    }
}

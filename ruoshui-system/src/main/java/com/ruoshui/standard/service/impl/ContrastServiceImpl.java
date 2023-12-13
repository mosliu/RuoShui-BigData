package com.ruoshui.standard.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.standard.dto.ContrastDto;
import com.ruoshui.standard.entity.ContrastEntity;
import com.ruoshui.standard.mapper.ContrastDao;
import com.ruoshui.standard.mapstruct.ContrastMapper;
import com.ruoshui.standard.service.ContrastService;
import com.ruoshui.standard.vo.ContrastTreeVo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ruoshui.common.utils.SecurityUtils.getUsername;

/**
 * <p>
 * 对照表信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-09-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ContrastServiceImpl extends BaseServiceImpl<ContrastDao, ContrastEntity> implements ContrastService {

    @Resource
    private ContrastDao contrastDao;

    @Resource
    private ContrastMapper contrastMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContrastEntity saveContrast(ContrastDto contrastDto) {
        ContrastEntity contrast = contrastMapper.toEntity(contrastDto);
        contrast.setCreateBy(getUsername());
        contrastDao.insert(contrast);
        return contrast;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContrastEntity updateContrast(ContrastDto contrastDto) {
        ContrastEntity contrast = contrastMapper.toEntity(contrastDto);
        contrast.setUpdateBy(getUsername());
        contrastDao.updateById(contrast);
        return contrast;
    }

    @Override
    public ContrastEntity getContrastById(String id) {
        ContrastEntity contrastEntity = super.getById(id);
        return contrastEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContrastById(String id) {
        contrastDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContrastBatch(List<String> ids) {
        contrastDao.deleteBatchIds(ids);
    }

    @Override
    public List<ContrastTreeVo> getContrastTree() {
        List<ContrastTreeVo> list = new ArrayList<>();
        List<ContrastEntity> contrastEntityList = contrastDao.selectList(Wrappers.emptyWrapper());
        Map<String, List<ContrastEntity>> sourceMap = contrastEntityList.stream().collect(Collectors.groupingBy(ContrastEntity::getSourceId));
        Iterator<Map.Entry<String, List<ContrastEntity>>> sourceIterator = sourceMap.entrySet().iterator();
        while (sourceIterator.hasNext()) {
            Map.Entry<String, List<ContrastEntity>> sourceEntry = sourceIterator.next();
            String sourceId = sourceEntry.getKey();
            List<ContrastEntity> sourceList = sourceEntry.getValue();
            String sourceName = sourceList.get(0).getSourceName();
            ContrastTreeVo sourceTree = new ContrastTreeVo();
            sourceTree.setId(sourceId);
            sourceTree.setLabel(sourceName);
            Map<String, List<ContrastEntity>> tableMap = sourceList.stream().collect(Collectors.groupingBy(ContrastEntity::getTableId));
            Iterator<Map.Entry<String, List<ContrastEntity>>> tableIterator = tableMap.entrySet().iterator();
            List<ContrastTreeVo> tableTreeList = new ArrayList<>();
            while (tableIterator.hasNext()) {
                Map.Entry<String, List<ContrastEntity>> tableEntry = tableIterator.next();
                String tableId = tableEntry.getKey();
                List<ContrastEntity> tableList = tableEntry.getValue();
                String tableName = tableList.get(0).getTableName();
                String tableComment = tableList.get(0).getTableComment();
                ContrastTreeVo tableTree = new ContrastTreeVo();
                tableTree.setId(tableId);
                tableTree.setLabel(tableName);
                tableTree.setName(tableComment);
                List<ContrastTreeVo> columnTreeList = tableList.stream().map(s -> {
                    ContrastTreeVo columnTree = new ContrastTreeVo();
                    columnTree.setId(s.getId());
                    columnTree.setLabel(s.getColumnName());
                    columnTree.setName(s.getColumnComment());
                    columnTree.setData(s);
                    return columnTree;
                }).collect(Collectors.toList());
                tableTree.setChildren(columnTreeList);
                tableTreeList.add(tableTree);
            }
            sourceTree.setChildren(tableTreeList);
            list.add(sourceTree);
        }
        return list;
    }

    @Override
    public IPage<ContrastEntity> statistic(IPage<ContrastEntity> page, Wrapper<ContrastEntity> queryWrapper) {
        return contrastDao.statistic(page, queryWrapper);
    }
}

package com.ruoshui.metadata.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoshui.database.utils.SecurityUtil;
import com.ruoshui.databse.core.DataConstant;
import com.ruoshui.databse.core.RedisConstant;
import com.ruoshui.metadata.dto.MetadataColumnDto;
import com.ruoshui.metadata.entity.MetadataAuthorizeEntity;
import com.ruoshui.metadata.entity.MetadataColumnEntity;
import com.ruoshui.metadata.entity.MetadataSourceEntity;
import com.ruoshui.metadata.entity.MetadataTableEntity;
import com.ruoshui.metadata.enums.DataLevel;
import com.ruoshui.metadata.mapper.MetadataColumnDao;
import com.ruoshui.metadata.query.MetadataColumnQuery;
import com.ruoshui.metadata.service.MetadataColumnMapper;
import com.ruoshui.metadata.service.MetadataColumnService;
import com.ruoshui.metadata.vo.MetadataTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.common.core.redis.RedisCache;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ruoshui.common.utils.SecurityUtils.getUsername;

/**
 * <p>
 * 元数据信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MetadataColumnServiceImpl extends BaseServiceImpl<MetadataColumnDao, MetadataColumnEntity> implements MetadataColumnService {

    @Autowired
    private MetadataColumnDao metadataColumnDao;

    @Autowired
    private MetadataColumnMapper metadataColumnMapper;

    @Autowired
    private RedisCache redisService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataColumnEntity saveMetadataColumn(MetadataColumnDto metadataColumnDto) {
        MetadataColumnEntity metadataColumn = metadataColumnMapper.toEntity(metadataColumnDto);
        metadataColumnDao.insert(metadataColumn);
        return metadataColumn;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataColumnEntity updateMetadataColumn(MetadataColumnDto metadataColumnDto) {
        MetadataColumnEntity metadataColumn = metadataColumnMapper.toEntity(metadataColumnDto);
        metadataColumnDao.updateById(metadataColumn);
        return metadataColumn;
    }

    @Override
    public MetadataColumnEntity getMetadataColumnById(String id) {
        MetadataColumnEntity metadataColumnEntity = super.getById(id);
        return metadataColumnEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataColumnById(String id) {
        metadataColumnDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataColumnBatch(List<String> ids) {
        metadataColumnDao.deleteBatchIds(ids);
    }

    @Override
    public List<MetadataTreeVo> getDataMetadataTree(String level, MetadataColumnQuery metadataColumnQuery) {
        List<MetadataSourceEntity> sourceList = (List<MetadataSourceEntity>) redisService.getCacheObject(RedisConstant.METADATA_SOURCE_KEY);
        List<MetadataTreeVo> list = new ArrayList<>();
        if(sourceList.size()>0){
            for(int i=0;i<sourceList.size();i++){
                MetadataTreeVo tree = new MetadataTreeVo();
                tree.setId(sourceList.get(i).getId());
                tree.setType(DataLevel.DATABASE.getKey());
                tree.setLabel(sourceList.get(i).getSourceName());
                tree.setName(sourceList.get(i).getSourceName());
                if (DataLevel.getLevel(level).getLevel() >= DataLevel.TABLE.getLevel()) {
                    tree.setChildren(getTableChildrens(sourceList.get(i).getId(), level, metadataColumnQuery.getTableId()));
                }
                list.add(tree);
            }
        }
        return list;
    }

    private List<MetadataTreeVo> getTableChildrens(String id, String level, String tableId) {
        List<MetadataTableEntity> tableList = (List<MetadataTableEntity>) redisService.hget(RedisConstant.METADATA_TABLE_KEY, id);
        List<MetadataTreeVo> children = new ArrayList<>();
        if(tableList.size()>0){
            for(int i=0;i<tableList.size();i++){
                MetadataTreeVo tree = new MetadataTreeVo();
                tree.setId(tableList.get(i).getId());
                tree.setType(DataLevel.TABLE.getKey());
                tree.setName(tableList.get(i).getTableComment());
                tree.setCode(tableList.get(i).getTableName());
                tree.setLabel(StrUtil.isBlank(tableList.get(i).getTableComment()) ? tableList.get(i).getTableName() : tableList.get(i).getTableComment());
                if (DataLevel.getLevel(level).getLevel() >= DataLevel.COLUMN.getLevel()) {
                    tree.setChildren(getColumnChildrens(tableList.get(i).getId()));
                }
                children.add(tree);
            }
        }
        return children;
    }

    private List<MetadataTreeVo> getColumnChildrens(String id) {
        List<MetadataColumnEntity> columnList = (List<MetadataColumnEntity>) redisService.hget(RedisConstant.METADATA_COLUMN_KEY, id);
        List<MetadataTreeVo> children = new ArrayList<>();
        if(null !=columnList && columnList.size()>0){
            for(int i=0;i<columnList.size();i++){
                MetadataTreeVo tree = new MetadataTreeVo();
                tree.setId(columnList.get(i).getId());
                tree.setType(DataLevel.COLUMN.getKey());
                tree.setName(columnList.get(i).getColumnComment());
                tree.setCode(columnList.get(i).getColumnName());
                tree.setLabel(StrUtil.isBlank(columnList.get(i).getColumnComment()) ? columnList.get(i).getColumnName() : columnList.get(i).getColumnComment());
                children.add(tree);
            }
        }
        return children;
    }

    @Override
    public List<MetadataColumnEntity> getDataMetadataColumnList(MetadataColumnQuery metadataColumnQuery) {
        if (StrUtil.isBlank(metadataColumnQuery.getTableId())) {
            throw new RuntimeException("数据表不能为空");
        }
        List<MetadataColumnEntity> columnList = (List<MetadataColumnEntity>) redisService.hget(RedisConstant.METADATA_COLUMN_KEY, metadataColumnQuery.getTableId());
        Stream<MetadataColumnEntity> stream = Optional.ofNullable(columnList).orElseGet(ArrayList::new).stream();
        return stream.collect(Collectors.toList());
    }

    @Override
    public <E extends IPage<MetadataColumnEntity>> E pageWithAuth(E page, Wrapper<MetadataColumnEntity> queryWrapper) {
        boolean admin = SecurityUtil.isAdmin();
        List<String> roles = new ArrayList<>();
        if (!admin) {
            roles = SecurityUtil.getUserRoleIds();
        }
        return metadataColumnDao.selectPageWithAuth(page, queryWrapper, roles);
    }
}

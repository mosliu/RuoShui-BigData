package com.ruoshui.metadata.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoshui.common.core.redis.RedisCache;
import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.core.database.core.RedisConstant;
import com.ruoshui.common.core.redis.RedisCache;
import com.ruoshui.metadata.dto.MetadataTableDto;
import com.ruoshui.metadata.entity.MetadataTableEntity;
import com.ruoshui.metadata.mapper.MetadataTableDao;
import com.ruoshui.metadata.mapstruct.MetadataTableMapper;
import com.ruoshui.metadata.query.MetadataTableQuery;
import com.ruoshui.metadata.service.MetadataTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ruoshui.common.utils.SecurityUtils.getUsername;

/**
 * <p>
 * 数据库表信息表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MetadataTableServiceImpl extends BaseServiceImpl<MetadataTableDao, MetadataTableEntity> implements MetadataTableService {

    @Autowired
    private MetadataTableDao metadataTableDao;

    @Autowired
    private MetadataTableMapper metadataTableMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataTableEntity saveMetadataTable(MetadataTableDto metadataTableDto) {
        MetadataTableEntity metadataTableEntity = metadataTableMapper.toEntity(metadataTableDto);
        metadataTableDao.insert(metadataTableEntity);
        return metadataTableEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataTableEntity updateMetadataTable(MetadataTableDto metadataTableDto) {
        MetadataTableEntity metadataTableEntity = metadataTableMapper.toEntity(metadataTableDto);
        metadataTableDao.updateById(metadataTableEntity);
        return metadataTableEntity;
    }

    @Override
    public MetadataTableEntity getMetadataTableById(String id) {
        MetadataTableEntity metadataTableEntity = super.getById(id);
        return metadataTableEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataTableById(String id) {
        metadataTableDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataTableBatch(List<String> ids) {
        metadataTableDao.deleteBatchIds(ids);
    }

    @Override
    public List<MetadataTableEntity> getDataMetadataTableList(MetadataTableQuery metadataTableQuery) {

        if (StrUtil.isBlank(metadataTableQuery.getSourceId())) {
            throw new RuntimeException("数据源不能为空");
        }
        List<MetadataTableEntity> tableList = (List<MetadataTableEntity>) redisCache.hget(RedisConstant.METADATA_TABLE_KEY, metadataTableQuery.getSourceId());
        Stream<MetadataTableEntity> stream = Optional.ofNullable(tableList).orElseGet(ArrayList::new).stream();
        return stream.collect(Collectors.toList());
    }

    @Override
    public <E extends IPage<MetadataTableEntity>> E pageWithAuth(E page, Wrapper<MetadataTableEntity> queryWrapper) {
        List<String> roles = new ArrayList<>();
        return metadataTableDao.selectPageWithAuth(page, queryWrapper, roles);
    }
}

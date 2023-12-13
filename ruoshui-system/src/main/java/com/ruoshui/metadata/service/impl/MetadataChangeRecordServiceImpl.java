package com.ruoshui.metadata.service.impl;


import com.ruoshui.core.database.base.BaseServiceImpl;
import com.ruoshui.metadata.dto.MetadataChangeRecordDto;
import com.ruoshui.metadata.entity.MetadataChangeRecordEntity;
import com.ruoshui.metadata.mapper.MetadataChangeRecordDao;
import com.ruoshui.metadata.mapstruct.MetadataChangeRecordMapper;
import com.ruoshui.metadata.service.MetadataChangeRecordService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ruoshui.common.utils.SecurityUtils.getUsername;

/**
 * <p>
 * 元数据变更记录表 服务实现类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-30
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MetadataChangeRecordServiceImpl extends BaseServiceImpl<MetadataChangeRecordDao, MetadataChangeRecordEntity> implements MetadataChangeRecordService {

    @Resource
    private MetadataChangeRecordDao metadataChangeRecordDao;

    @Resource
    private MetadataChangeRecordMapper metadataChangeRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataChangeRecordEntity saveMetadataChangeRecord(MetadataChangeRecordDto metadataChangeRecordDto) {
        MetadataChangeRecordEntity metadataChangeRecord = metadataChangeRecordMapper.toEntity(metadataChangeRecordDto);
        metadataChangeRecord.setCreateBy(getUsername());
        metadataChangeRecordDao.insert(metadataChangeRecord);
        return metadataChangeRecord;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataChangeRecordEntity updateMetadataChangeRecord(MetadataChangeRecordDto metadataChangeRecordDto) {
        MetadataChangeRecordEntity metadataChangeRecord = metadataChangeRecordMapper.toEntity(metadataChangeRecordDto);
        metadataChangeRecord.setUpdateBy(getUsername());
        metadataChangeRecordDao.updateById(metadataChangeRecord);
        return metadataChangeRecord;
    }

    @Override
    public MetadataChangeRecordEntity getMetadataChangeRecordById(String id) {
        MetadataChangeRecordEntity metadataChangeRecordEntity = super.getById(id);
        return metadataChangeRecordEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataChangeRecordById(String id) {
        metadataChangeRecordDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMetadataChangeRecordBatch(List<String> ids) {
        metadataChangeRecordDao.deleteBatchIds(ids);
    }
}

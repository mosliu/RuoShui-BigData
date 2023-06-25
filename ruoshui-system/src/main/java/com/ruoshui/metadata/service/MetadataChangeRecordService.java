package com.ruoshui.metadata.service;



import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.metadata.dto.MetadataChangeRecordDto;
import com.ruoshui.metadata.entity.MetadataChangeRecordEntity;

import java.util.List;

/**
 * <p>
 * 元数据变更记录表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-30
 */
public interface MetadataChangeRecordService extends BaseService<MetadataChangeRecordEntity> {

    MetadataChangeRecordEntity saveMetadataChangeRecord(MetadataChangeRecordDto metadataChangeRecord);

    MetadataChangeRecordEntity updateMetadataChangeRecord(MetadataChangeRecordDto metadataChangeRecord);

    MetadataChangeRecordEntity getMetadataChangeRecordById(String id);

    void deleteMetadataChangeRecordById(String id);

    void deleteMetadataChangeRecordBatch(List<String> ids);
}

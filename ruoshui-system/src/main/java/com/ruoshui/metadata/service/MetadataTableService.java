package com.ruoshui.metadata.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoshui.core.database.base.BaseService;
import com.ruoshui.metadata.dto.MetadataTableDto;
import com.ruoshui.metadata.entity.MetadataTableEntity;
import com.ruoshui.metadata.query.MetadataTableQuery;

import java.util.List;

/**
 * <p>
 * 数据库表信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
public interface MetadataTableService extends BaseService<MetadataTableEntity> {

    MetadataTableEntity saveMetadataTable(MetadataTableDto metadataTable);

    MetadataTableEntity updateMetadataTable(MetadataTableDto metadataTable);

    MetadataTableEntity getMetadataTableById(String id);

    void deleteMetadataTableById(String id);

    void deleteMetadataTableBatch(List<String> ids);

    List<MetadataTableEntity> getDataMetadataTableList(MetadataTableQuery metadataTableQuery);

    <E extends IPage<MetadataTableEntity>> E pageWithAuth(E page, Wrapper<MetadataTableEntity> queryWrapper);
}

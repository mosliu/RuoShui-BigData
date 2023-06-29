package com.ruoshui.async;


import cn.hutool.core.collection.CollUtil;
import com.ruoshui.common.database.constants.DbQueryProperty;
import com.ruoshui.common.database.service.DataSourceFactory;
import com.ruoshui.common.database.service.DbQuery;
import com.ruoshui.core.database.core.DbColumn;
import com.ruoshui.core.database.core.DbTable;
import com.ruoshui.market.dto.ApiLogDto;
import com.ruoshui.market.service.ApiLogService;
import com.ruoshui.metadata.dto.DbSchema;
import com.ruoshui.metadata.entity.MetadataColumnEntity;
import com.ruoshui.metadata.entity.MetadataSourceEntity;
import com.ruoshui.metadata.entity.MetadataTableEntity;
import com.ruoshui.metadata.enums.SyncStatus;
import com.ruoshui.metadata.mapper.MetadataColumnDao;
import com.ruoshui.metadata.mapper.MetadataSourceDao;
import com.ruoshui.metadata.mapper.MetadataTableDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 异步处理
 */
@Slf4j
@Component
public class AsyncTask {

    @Autowired
    private ApiLogService apiLogService;

    @Autowired
    private DataSourceFactory dataSourceFactory;

    @Autowired
    private MetadataSourceDao metadataSourceDao;

    @Autowired
    private MetadataTableDao metadataTableDao;

    @Autowired
    private MetadataColumnDao metadataColumnDao;

    @Async("taskExecutor")
    public void  doTask(ApiLogDto apiLogDto) {

        apiLogService.saveApiLog(apiLogDto);
    }

    @Async("taskExecutor")
    public void doTask(MetadataSourceEntity dataSource) {
        Boolean completed = false;
        try {
            long start = System.currentTimeMillis();
            DbSchema dbSchema = dataSource.getDbSchema();
            DbQueryProperty dbQueryProperty = new DbQueryProperty(dataSource.getDbType(), dbSchema.getHost(),
                    dbSchema.getUsername(), dbSchema.getPassword(), dbSchema.getPort(), dbSchema.getDbName(), dbSchema.getSid());
            DbQuery dbQuery = dataSourceFactory.createDbQuery(dbQueryProperty);
            List<DbTable> tables = dbQuery.getTables(dbSchema.getDbName());
            if (CollUtil.isNotEmpty(tables)) {
                List<MetadataTableEntity> metadataTableEntityList = tables.stream().map(table -> {
                    MetadataTableEntity metadataTable = new MetadataTableEntity();
                    metadataTable.setSourceId(dataSource.getId());
                    metadataTable.setTableName(table.getTableName());
                    metadataTable.setTableComment(table.getTableComment());
                    return metadataTable;
                }).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(metadataTableEntityList)) {
                    metadataTableEntityList.stream().forEach(table -> {
                        metadataTableDao.insert(table);
                        List<DbColumn> columns = dbQuery.getTableColumns(dbSchema.getDbName(), table.getTableName());
                        if (CollUtil.isNotEmpty(columns)) {
                            List<MetadataColumnEntity> metadataColumnEntityList = columns.stream().map(column -> {
                                MetadataColumnEntity metadataColumn = new MetadataColumnEntity();
                                metadataColumn.setSourceId(dataSource.getId());
                                metadataColumn.setTableId(table.getId());
                                metadataColumn.setColumnName(column.getColName());
                                metadataColumn.setColumnComment(column.getColComment());
                                metadataColumn.setColumnKey(column.getColKey() ? "1" : "0");
                                metadataColumn.setColumnNullable(column.getNullable() ? "1" : "0");
                                metadataColumn.setColumnPosition(column.getColPosition().toString());
                                metadataColumn.setDataType(column.getDataType());
                                metadataColumn.setDataLength(column.getDataLength());
                                metadataColumn.setDataPrecision(column.getDataPrecision());
                                metadataColumn.setDataScale(column.getDataScale());
                                metadataColumn.setDataDefault(column.getDataDefault());
                                return metadataColumn;
                            }).collect(Collectors.toList());
                            if (CollUtil.isNotEmpty(metadataColumnEntityList)) {
                                metadataColumnEntityList.stream().forEach(column -> metadataColumnDao.insert(column));
                            }
                        }
                    });
                }
            }
            dataSource.setIsSync(SyncStatus.IsSync.getKey());
            metadataSourceDao.updateById(dataSource);
            completed = true;
            log.info("异步任务执行完成！耗时{}秒", (System.currentTimeMillis() - start / 1000));
        }finally {
                if(!completed){
                    dataSource.setIsSync(SyncStatus.ErrSync.getKey());
                    metadataSourceDao.updateById(dataSource);
                    log.info("异步任务执行失败！");
                }
            }

    }
}

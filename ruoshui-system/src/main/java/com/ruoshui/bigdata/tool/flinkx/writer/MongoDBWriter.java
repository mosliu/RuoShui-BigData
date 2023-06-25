package com.ruoshui.bigdata.tool.flinkx.writer;

import com.google.common.collect.Maps;
import com.ruoshui.bigdata.dto.UpsertInfo;
import com.ruoshui.bigdata.entity.JobDatasource;
import com.ruoshui.bigdata.tool.pojo.FlinkxMongoDBPojo;

import java.util.Map;

public class MongoDBWriter extends BaseWriterPlugin implements FlinkxWriterInterface {
    @Override
    public String getName() {
        return "mongodbwriter";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }


    @Override
    public Map<String, Object> buildMongoDB(FlinkxMongoDBPojo plugin) {
        //构建
        Map<String, Object> writerObj = Maps.newLinkedHashMap();
        JobDatasource dataSource = plugin.getJdbcDatasource();
        writerObj.put("name", getName());
        Map<String, Object> parameterObj = Maps.newLinkedHashMap();
        parameterObj.put("url", dataSource.getJdbcUrl()+""+dataSource.getDatabaseName());
		parameterObj.put("database",dataSource.getDatabaseName());
        parameterObj.put("collectionName", plugin.getWriterTable());
        parameterObj.put("column", plugin.getColumns());
		parameterObj.put("writeMode", "insert");
        UpsertInfo upsert = plugin.getUpsertInfo();
        if (upsert != null) {
            parameterObj.put("upsertInfo", upsert);
        }
        writerObj.put("parameter", parameterObj);
        return writerObj;
    }
}

package com.ruoshui.bigdata.tool.datax.writer;

import com.google.common.collect.Maps;
import com.ruoshui.bigdata.dto.UpsertInfo;
import com.ruoshui.bigdata.entity.JobDatasource;
import com.ruoshui.bigdata.tool.pojo.DataxMongoDBPojo;
import com.ruoshui.core.util.Constants;

import java.util.Map;

public class MongoDBWriter extends BaseWriterPlugin implements DataxWriterInterface {
    @Override
    public String getName() {
        return "mongodbwriter";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }


    @Override
    public Map<String, Object> buildMongoDB(DataxMongoDBPojo plugin) {
        //构建
        Map<String, Object> writerObj = Maps.newLinkedHashMap();
        JobDatasource dataSource = plugin.getJdbcDatasource();
        writerObj.put("name", getName());
        Map<String, Object> parameterObj = Maps.newLinkedHashMap();
        String[] addressList = null;
        String str = dataSource.getJdbcUrl().replace(Constants.MONGO_URL_PREFIX, Constants.STRING_BLANK);
        if (str.contains(Constants.SPLIT_AT) && str.contains(Constants.SPLIT_DIVIDE)) {
            addressList = str.substring(str.indexOf(Constants.SPLIT_AT) + 1, str.indexOf(Constants.SPLIT_DIVIDE)).split(Constants.SPLIT_COMMA);
        } else if (str.contains(Constants.SPLIT_DIVIDE)) {
            addressList = str.substring(0, str.indexOf(Constants.SPLIT_DIVIDE)).split(Constants.SPLIT_COMMA);
        }
        parameterObj.put("address", addressList);
        parameterObj.put("userName", dataSource.getJdbcUsername() == null ? Constants.STRING_BLANK : dataSource.getJdbcUsername());
        parameterObj.put("userPassword", dataSource.getJdbcPassword() == null ? Constants.STRING_BLANK : dataSource.getJdbcPassword());
        parameterObj.put("dbName", dataSource.getDatabaseName());
        parameterObj.put("collectionName", plugin.getWriterTable());
        parameterObj.put("column", plugin.getColumns());
        UpsertInfo upsert = plugin.getUpsertInfo();
        if (upsert != null) {
            parameterObj.put("upsertInfo", upsert);
        }
        writerObj.put("parameter", parameterObj);
        return writerObj;
    }
}

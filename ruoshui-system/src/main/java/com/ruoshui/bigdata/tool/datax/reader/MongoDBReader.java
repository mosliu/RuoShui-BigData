package com.ruoshui.bigdata.tool.datax.reader;

import com.google.common.collect.Maps;
import com.ruoshui.bigdata.entity.JobDatasource;
import com.ruoshui.bigdata.tool.pojo.DataxMongoDBPojo;
import com.ruoshui.core.util.Constants;

import java.util.Map;

public class MongoDBReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "mongodbreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }

    public Map<String, Object> buildMongoDB(DataxMongoDBPojo plugin) {
        //构建
        JobDatasource dataSource = plugin.getJdbcDatasource();
        Map<String, Object> readerObj = Maps.newLinkedHashMap();
        readerObj.put("name", getName());
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
        parameterObj.put("collectionName", plugin.getReaderTable());
        parameterObj.put("column", plugin.getColumns());
        readerObj.put("parameter", parameterObj);
        return readerObj;
    }
}

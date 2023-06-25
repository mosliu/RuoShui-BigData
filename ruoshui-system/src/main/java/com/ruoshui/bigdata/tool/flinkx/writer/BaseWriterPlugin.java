package com.ruoshui.bigdata.tool.flinkx.writer;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ruoshui.bigdata.util.AESUtil;
import com.ruoshui.bigdata.tool.flinkx.BaseFlinkxPlugin;
import com.ruoshui.bigdata.tool.pojo.FlinkxHbasePojo;
import com.ruoshui.bigdata.tool.pojo.FlinkxHivePojo;
import com.ruoshui.bigdata.tool.pojo.FlinkxMongoDBPojo;
import com.ruoshui.bigdata.tool.pojo.FlinkxRdbmsPojo;
import com.ruoshui.core.util.Constants;
import com.ruoshui.bigdata.entity.JobDatasource;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * flinkx writer base
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName BaseWriterPlugin
 * @Version 1.0
 * @since 2019/8/2 16:28
 */
public abstract class BaseWriterPlugin extends BaseFlinkxPlugin {
    @Override
    public Map<String, Object> build(FlinkxRdbmsPojo plugin) {
        Map<String, Object> writerObj = Maps.newLinkedHashMap();
        writerObj.put("name", getName());

        Map<String, Object> parameterObj = Maps.newLinkedHashMap();
//        parameterObj.put("writeMode", "insert");
        JobDatasource jobDatasource = plugin.getJobDatasource();
        parameterObj.put("username", AESUtil.decrypt(jobDatasource.getJdbcUsername()));
        parameterObj.put("password", AESUtil.decrypt(jobDatasource.getJdbcPassword()));
		//类型
        parameterObj.put("writeMode", "insert");
		parameterObj.put("column", plugin.getRdbmsColumns());
        parameterObj.put("preSql", splitSql(plugin.getPreSql()));
        parameterObj.put("postSql", splitSql(plugin.getPostSql()));

        Map<String, Object> connectionObj = Maps.newLinkedHashMap();
        connectionObj.put("table", plugin.getTables());
        connectionObj.put("jdbcUrl", jobDatasource.getJdbcUrl());

        parameterObj.put("connection", ImmutableList.of(connectionObj));
        writerObj.put("parameter", parameterObj);

        return writerObj;
    }

    private String[] splitSql(String sql) {
        String[] sqlArr = null;
        if (StringUtils.isNotBlank(sql)) {
            Pattern p = Pattern.compile("\r\n|\r|\n|\n\r");
            Matcher m = p.matcher(sql);
            String sqlStr = m.replaceAll(Constants.STRING_BLANK);
            sqlArr = sqlStr.split(Constants.SPLIT_COLON);
        }
        return sqlArr;
    }

    @Override
    public Map<String, Object> buildHive(FlinkxHivePojo flinkxHivePojo) {
        return null;
    }


    @Override
    public Map<String, Object> buildHbase(FlinkxHbasePojo flinkxHbasePojo) {
        return null;
    }

    @Override
    public Map<String, Object> buildMongoDB(FlinkxMongoDBPojo plugin) {
        return null;
    }

    /**
     * 默认的字段是 ["column1","column2"],如果不同 则需要覆盖掉
     * @param columns
     * @return
     */
    @Override
    public List<Object> getColumn(List<String> columns) {
        List<Object> data = Lists.newArrayList();
        columns.forEach(c -> {
            data.add(c.split(Constants.SPLIT_SCOLON)[0]);
        });
        return data;
    }
}
package com.ruoshui.common.datasource;

import com.dtstack.dtcenter.loader.cache.pool.config.PoolConfig;
import com.dtstack.dtcenter.loader.client.ClientCache;
import com.dtstack.dtcenter.loader.client.IClient;
import com.dtstack.dtcenter.loader.dto.SqlQueryDTO;
import com.dtstack.dtcenter.loader.dto.source.HiveSourceDTO;
import com.dtstack.dtcenter.loader.dto.source.OracleSourceDTO;
import com.dtstack.dtcenter.loader.source.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class HiveSourse {
    private static Logger logger = LoggerFactory.getLogger(HiveSourse.class);
    private static final String SQL = "SELECT * FROM ";// 数据库操作
    private static PreparedStatement pst = null;// 事务对象

    public static Connection getconn(String url, String username, String password,String schema,String defaultFS,String config){
        HiveSourceDTO sourceDTO = HiveSourceDTO.builder()
                .url(url)
                .username(username)
                .password(password)
                .schema(schema)
                .defaultFS(defaultFS)
                .config(config)
                .poolConfig(PoolConfig.builder().build())
                .build();
        IClient client = ClientCache.getClient(DataSourceType.HIVE.getVal());
        Boolean isConnected = client.testCon(sourceDTO);
        if (Boolean.FALSE.equals(isConnected)) {
            throw new RuntimeException("connection exception");
        }
        return client.getCon(sourceDTO);
    }

    public static Map<String,Object> getconns(String url, String username, String password,String schema,String defaultFS,String config) {
        Map<String,Object> map = new HashMap<>();
        HiveSourceDTO sourceDTO = HiveSourceDTO.builder()
                .url(url)
                .username(username)
                .password(password)
                .schema(schema)
                .defaultFS(defaultFS)
                .config(config)
                .poolConfig(PoolConfig.builder().build())
                .build();
        IClient client = ClientCache.getClient(DataSourceType.HIVE.getVal());
        Boolean isConnected = client.testCon(sourceDTO);
        if (Boolean.FALSE.equals(isConnected)) {
            throw new RuntimeException("connection exception");
        }
        map.put("client",client);
        map.put("source",sourceDTO);
        return map;
    }

    //关闭数据库连接
    public static void CloseCon(Connection con) throws Exception {
        if (con != null) {
            con.close();
            logger.info("已断开与数据库的连接！");
        }
    }

    /**
     * 无结果查询
     */
    public static Boolean executeSqlWithoutResultSet(IClient client, HiveSourceDTO source, String sql) {
        SqlQueryDTO queryDTO = SqlQueryDTO.builder().sql(sql).build();
        Boolean aBoolean = client.executeSqlWithoutResultSet(source, queryDTO);
        if (!aBoolean) {
            logger.error("自定义sql执行：" + sql + " 失败\n");
            throw new RuntimeException("执行失败");
        }
        return  aBoolean;
    }



}

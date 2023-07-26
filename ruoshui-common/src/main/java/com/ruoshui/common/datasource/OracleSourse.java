package com.ruoshui.common.datasource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtstack.dtcenter.loader.cache.pool.config.PoolConfig;
import com.dtstack.dtcenter.loader.client.ClientCache;
import com.dtstack.dtcenter.loader.client.IClient;
import com.dtstack.dtcenter.loader.dto.SqlQueryDTO;
import com.dtstack.dtcenter.loader.dto.source.OracleSourceDTO;
import com.dtstack.dtcenter.loader.source.DataSourceType;
import com.ruoshui.common.domian.Column;
import com.ruoshui.common.domian.OrcTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleSourse {

    private static Logger logger = LoggerFactory.getLogger(ClickhouseSource.class);
    private static final String SQL = "SELECT * FROM ";// 数据库操作
    private static PreparedStatement pst = null;// 事务对象

    public static Connection getconn(String url, String username, String password,String schema) {
        OracleSourceDTO source = OracleSourceDTO.builder()
                .url(url)
                .username(username)
                .password(password)
                .schema(schema)
                .poolConfig(new PoolConfig())
                .build();
        //获取连接
        IClient client = ClientCache.getClient(DataSourceType.Oracle.getVal());
        Boolean isConnected = client.testCon(source);
        if (Boolean.FALSE.equals(isConnected)) {
            throw new RuntimeException("connection exception");
        }
        return client.getCon(source);
    }

    public static Map<String,Object> getconns(String url, String username, String password,String schema) {
        Map<String,Object> map = new HashMap<>();
        OracleSourceDTO source = OracleSourceDTO.builder()
                .url(url)
                .username(username)
                .password(password)
                .schema(schema)
                .poolConfig(new PoolConfig())
                .build();
        //获取连接
        IClient client = ClientCache.getClient(DataSourceType.Oracle.getVal());
        Boolean isConnected = client.testCon(source);
        if (Boolean.FALSE.equals(isConnected)) {
            throw new RuntimeException("connection exception");
        }
        map.put("client",client);
        map.put("source",source);
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
    public static Boolean executeSqlWithoutResultSet(IClient client, OracleSourceDTO source, String sql) {
        SqlQueryDTO queryDTO = SqlQueryDTO.builder().sql(sql).build();
        Boolean aBoolean = client.executeSqlWithoutResultSet(source, queryDTO);
        if (!aBoolean) {
            logger.error("自定义sql执行：" + sql + " 失败\n");
            throw new RuntimeException("执行失败");
        }
        return  aBoolean;
    }

    /**
     * 自定义查询
     */
    public static List<Map<String, Object>> customQuery(IClient client, OracleSourceDTO source, String sql,List<String> parameters) {

        // 预编译查询
        List<Object> preFields = new ArrayList<>();
        if(parameters!=null&&parameters.size()>0){
            for(String parameter : parameters){
                preFields.add(parameter);
            }
        }
        SqlQueryDTO queryDTO = SqlQueryDTO.builder().sql(sql).preFields(preFields).build();
        List<Map<String, Object>> result = client.executeQuery(source, queryDTO);
        return  result;
    }

    /**
     * 修改ORC数据库的注释
     *
     * @param client      数据库连接对象
     * @param source      数据库连接对象
     * @param table       表名
     * @param name        字段名
     * @param comment     注释
     * @return
     */
    public static boolean updateFieldComment(IClient client, OracleSourceDTO source,  String table, String name, String comment) {
        String sql = "comment on column " + table + "." + name + " is " + " '" + comment + "'" + " ;";
            SqlQueryDTO queryDTO = SqlQueryDTO.builder().sql(sql).build();
            Boolean aBoolean = client.executeSqlWithoutResultSet(source, queryDTO);
            if (!aBoolean) {
                logger.error("修改Orc数据库的字段类型：" + sql + " 失败\n");
                throw new RuntimeException("执行失败");
            }
        return true;
    }

    /**
     * 修改orc数据库的字段名
     *
     * @param client      数据库连接对象
     * @param source      数据库连接对象
     * @param database    库名
     * @param table       表名
     * @param name        当前字段名
     * @param toname      修改字段名
     * @return
     */
    public static boolean updateFieldName(IClient client, OracleSourceDTO source,  String database, String table, String name, String toname) {
        String sql = "ALTER TABLE " + database + "." + table + " RENAME COLUMN " + name + " TO " + toname + ";";
        SqlQueryDTO queryDTO = SqlQueryDTO.builder().sql(sql).build();
        Boolean aBoolean = client.executeSqlWithoutResultSet(source, queryDTO);
        if (!aBoolean) {
            logger.error("修改Orc数据库的字段名：" + sql + " 失败\n");
            throw new RuntimeException("执行失败");
        }
        return true;
    }

    /**
     * 修改ORC数据库的字段类型
     *
     * @param client      数据库连接对象
     * @param source      数据库连接对象
     * @param database    库名
     * @param table       表名
     * @param name        当前字段名
     * @param type        字段类型
     * @return
     */
    public static boolean updateFieldType(IClient client, OracleSourceDTO source,  String database, String table, String name, String type) {
            String sql = "ALTER TABLE " + database + "." + table + " modify ( " + name + " " + type + ");";
            SqlQueryDTO queryDTO = SqlQueryDTO.builder().sql(sql).build();
            Boolean aBoolean = client.executeSqlWithoutResultSet(source, queryDTO);
            if (!aBoolean) {
                logger.error("修改CK数据库的字段类型：" + sql + " 失败\n");
                throw new RuntimeException("执行失败");
            }
        return true;
    }

    /**
     * 添加ORC数据库的字段
     *
     * @param client      数据库连接对象
     * @param source      数据库连接对象
     * @param database    库名
     * @param table       表名
     * @param name        字段名
     * @param type        字段类型
     * @return
     */
    public static boolean insertField(IClient client, OracleSourceDTO source,  String database, String table, String name, String type) {
            String sql = "ALTER TABLE " + database + "." + table + " ADD  " + name + " " + type  + ";";
            SqlQueryDTO queryDTO = SqlQueryDTO.builder().sql(sql).build();
            Boolean aBoolean = client.executeSqlWithoutResultSet(source, queryDTO);
            if (!aBoolean) {
                logger.error("添加Orc数据库的字段：" + sql + " 失败\n");
                throw new RuntimeException("执行失败");
            }
        return true;
    }

    /**
     * 删除Orc数据库的字段
     *
     * @param client      数据库连接对象
     * @param source      数据库连接对象
     * @param database    库名
     * @param table       表名
     * @param name        字段名
     * @return
     */
    public static boolean deleteField(IClient client, OracleSourceDTO source,  String database, String table, String name) {
        String sql = "ALTER TABLE " + database + "." + table + " DROP COLUMN " + name + " ;";
            SqlQueryDTO queryDTO = SqlQueryDTO.builder().sql(sql).build();
            Boolean aBoolean = client.executeSqlWithoutResultSet(source, queryDTO);
            if (!aBoolean) {
                logger.error("添加Orc数据库的字段：" + sql + " 失败\n");
                throw new RuntimeException("执行失败");
            }
        return true;
    }

    /**
     * 获取表中字段的所有注释
     *
     * @param tableName
     * @return
     */
    public static List<Column> getColumnComment(Connection conn, String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<Column> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("select  a.COLUMN_NAME, a.COMMENTS,b.DATA_TYPE,b.DATA_PRECISION, b.DATA_SCALE,b.CHAR_LENGTH from user_col_comments a left join user_tab_columns b on b.COLUMN_NAME=a.column_name and b.TABLE_NAME = a.table_name  where a.TABLE_NAME = '" + tableName + "' ORDER BY COLUMN_ID");
            while (rs.next()) {
                Column Column = new Column();
                Column.setName(rs.getString("COLUMN_NAME"));
                Column.setType(rs.getString("DATA_TYPE"));
                Column.setComment(rs.getString("COMMENTS"));
                Column.setDataPrecision(rs.getString("DATA_PRECISION"));
                Column.setDataScale(rs.getString("DATA_SCALE"));
                Column.setCharLength(rs.getString("CHAR_LENGTH"));
                columnComments.add(Column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    CloseCon(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return columnComments;
    }

    /**
     * 获取库中所有表名
     *
     * @param baseName
     * @return
     */
    public static List<OrcTable> getColumnComments(Connection conn, String baseName) {
        PreparedStatement pStemt = null;
        String tableSql = SQL + baseName;
        List<OrcTable> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("select table_name,comments from user_tab_comments ");
            while (rs.next()) {
                OrcTable OrcTable = new OrcTable();
                OrcTable.setDatabase(baseName);
                OrcTable.setTableName(rs.getString("table_name"));
                OrcTable.setComments(rs.getString("comments"));
                OrcTable.setCreateTableQuery("--");
                columnComments.add(OrcTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    CloseCon(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return columnComments;
    }

    /**
     * 執行sql查詢
     *
     * @auther xinjingruoshui
     * @time 2022年7月20日
     */
    public static Map<String, Object> executeQuerySql(Connection conn, String sql, int pageNum, int pageSize) throws Exception {

        if (pageNum == 1) {
            pageNum = 0;
        }
        int start = pageNum * pageSize;
        StringBuilder sql1 = new StringBuilder();// 拼接sql
        sql1.append(sql + "\n");
        sql1.append("limit " + start + "," + pageSize + "\n");
        System.out.println(sql1.toString());
        pst = conn.prepareStatement(sql1.toString());
        ResultSet result = pst.executeQuery();// 查询结果
        ResultSetMetaData rsmd = result.getMetaData();
        JSONArray tableTitle = new JSONArray();// 表格头
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            JSONObject tableTitle_Th = new JSONObject();// 表格头单元格
            tableTitle_Th.put("columnname", rsmd.getColumnName(i));// 字段名
            tableTitle_Th.put("tablename", rsmd.getTableName(i));// 表名
            tableTitle_Th.put("columnclassname", rsmd.getColumnClassName(i));// JAVA_数据类型
            tableTitle_Th.put("columntypename", rsmd.getColumnTypeName(i) + "(" + rsmd.getColumnDisplaySize(i) + ")");// DB_数据类型
            tableTitle.add(tableTitle_Th);// 保存到数组
        }
        JSONObject table = new JSONObject();// 所有查詢的數據
        JSONArray tableBody = new JSONArray();// 表格内容
        while (result.next()) {
            JSONArray tableRow = new JSONArray();// 表内容单元格
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String classname = rsmd.getColumnClassName(i);// 数据类型
                switch (classname) {
                    case "java.math.BigDecimal": {
                        tableRow.add(result.getBigDecimal(i));
                        break;
                    }
                    case "java.lang.Boolean": {
                        tableRow.add(result.getBoolean(i));
                        break;
                    }
                    case "java.lang.Byte": {
                        tableRow.add(result.getByte(i));
                        break;
                    }
                    case "java.util.Date": {
                        Date date = result.getDate(i);
                        String time = "";
                        if (date != null) {
                            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                        }
                        tableRow.add(time);
                        break;
                    }
                    case "java.sql.Date": {
                        java.sql.Date date = result.getDate(i);
                        String time = "";
                        if (date != null) {
                            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                        }
                        tableRow.add(time);
                        break;
                    }
                    case "java.lang.Double": {
                        tableRow.add(result.getDouble(i));
                        break;
                    }
                    case "java.lang.Float": {
                        tableRow.add(result.getFloat(i));
                        break;
                    }
                    case "java.lang.Integer": {
                        tableRow.add(result.getInt(i));
                        break;
                    }
                    case "java.lang.Long": {
                        tableRow.add(result.getLong(i));
                        break;
                    }
                    case "java.lang.String": {
                        tableRow.add(result.getString(i));
                        break;
                    }
                    case "java.sql.Timestamp": {
                        String str = "9999-12-12 00:00:00";
                        if (!"".equals(result.getString(i)) && result.getString(i) != null) {
                            str = result.getString(i);
                        }
                        tableRow.add(stampToDate(Double.valueOf(dateToStamp(str))));
                        break;
                    }
                    case "java.math.BigInteger": {
                        tableRow.add(result.getBigDecimal(i));
                        break;
                    }
                    default:
                        tableRow.add(result.getString(i));
                }
            }
            tableBody.add(tableRow);
        }

        table.put("tableTitle", tableTitle);
        table.put("tableBody", tableBody);

        Map<String, Object> remap = new HashMap<>();
        JSONArray tableTitle1 = table.getJSONArray("tableTitle");
        JSONArray tableBody1 = table.getJSONArray("tableBody");
        List<Map<Object, Object>> list = new ArrayList<>();
        List<Map<String, Object>> Datalist = new ArrayList<>();
        Object[] str = new String[tableTitle1.size()];
        for (int j = 0; j < tableTitle1.size(); j++) {
            JSONObject tableTitle_Th = (JSONObject) tableTitle1.get(j);
            Map<String, Object> tableData = new HashMap<>();
            tableData.put("dataItem", tableTitle_Th.get("columnname"));
            Datalist.add(tableData);
            str[j] = tableTitle_Th.get("columnname");
            System.out.print(tableTitle_Th.get("columnname") + "\t");
        }
        logger.info("\n------------------------------------------------------------------------------------------------------------------------");
        for (Object o : tableBody1) {
            Map<Object, Object> tables = new HashMap<>();
            JSONArray row = (JSONArray) o;
            for (int j = 0; j < row.size(); j++) {
                tables.put(str[j], row.get(j));
                System.out.print(row.get(j) + "\t");
            }
            list.add(tables);
            System.out.println();
        }
        Long x = executeQuerySqlNum(conn, sql);
        remap.put("tables", list);
        remap.put("tableData", Datalist);
        remap.put("total", x);
        return remap;
    }


    /**
     * 查詢數據縂數量
     *
     * @auther xinjingruoshui
     * @time 2022年7月20日
     */
    public static Long executeQuerySqlNum(Connection conn, String sql) throws Exception {
        StringBuilder sql1 = new StringBuilder();// 拼接sql
        sql1.append("select count(*) statistics from (");
        sql1.append(sql);
        sql1.append(")");
        pst = conn.prepareStatement(sql1.toString());
        ResultSet result = pst.executeQuery();// 查询结果
        Long i = 0L;
        while (result.next()) {
            i = result.getLong("statistics");
        }
        conn.close();// 关闭
        return i;
    }


    public static String stampToDate(Double time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time_Date = sdf.format(new java.util.Date((long) (time * 1000L)));
        return time_Date;

    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stamp = "";
        if (!"".equals(time)) {//时间不为空
            try {
                stamp = String.valueOf(sdf.parse(time).getTime() / 1000);
            } catch (Exception e) {
                System.out.println("参数为空！");
            }
        } else {    //时间为空
            long current_time = System.currentTimeMillis();  //获取当前时间
            stamp = String.valueOf(current_time / 1000);
        }
        return stamp;
    }

}

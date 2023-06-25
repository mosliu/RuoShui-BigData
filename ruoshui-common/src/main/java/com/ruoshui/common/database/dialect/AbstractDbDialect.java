package com.ruoshui.common.database.dialect;


import com.ruoshui.common.database.service.DbDialect;

/**
 * 方言抽象类
 *
 * @author yuwei
 * @since 2020-03-14
 */
public abstract class AbstractDbDialect implements DbDialect {

    @Override
    public String columns(String dbName, String tableName) {
        return "select name COLNAME,type DATATYPE,'' DATALENGTH, '' DATAPRECISION,'' DATASCALE, is_in_primary_key COLKEY,'' NULLABLE,rowNumberInAllBlocks() COLPOSITION,default_expression DATADEFAULT,comment COLCOMMENT  from system.columns where database = '" + dbName + "' and table = '" + tableName + "'";
    }

    @Override
    public String tables(String dbName) {
        return "SELECT name AS TABLENAME, '' AS TABLECOMMENT FROM system.tables where database = '" + dbName + "' ";
    }

    @Override
    public String buildPaginationSql(String originalSql, long offset, long count) {
        // 获取 分页实际条数
        StringBuilder sqlBuilder = new StringBuilder(originalSql);
        sqlBuilder.append(" LIMIT ").append(offset).append(" , ").append(count);
        return sqlBuilder.toString();
    }

    @Override
    public String count(String sql) {
        return "SELECT COUNT(*) FROM ( " + sql + " ) TEMP";
    }
}

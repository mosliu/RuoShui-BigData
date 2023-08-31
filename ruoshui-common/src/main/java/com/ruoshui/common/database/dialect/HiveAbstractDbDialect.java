package com.ruoshui.common.database.dialect;


import com.ruoshui.common.database.service.DbDialect;

/**
 * 方言抽象类
 *
 * @author xinjingruoshui
 * @since 2023-08-14
 */
public abstract class HiveAbstractDbDialect implements DbDialect {

    @Override
    public String columns(String dbName, String tableName) {
        return "desc  "+dbName+"."+tableName;
    }

    @Override
    public String tables(String dbName) {
        return "show tables in "+dbName;
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
        return "SELECT COUNT(1) FROM ( " + sql + " ) TEMP";
    }
}

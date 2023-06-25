package com.ruoshui.common.database.dialect;


import com.ruoshui.core.database.core.DbColumn;
import com.ruoshui.core.database.core.DbTable;
import org.springframework.jdbc.core.RowMapper;

/**
 * 未知 数据库方言
 *
 * @author yuwei
 * @since 2020-03-14
 */
public class UnknownDialect extends AbstractDbDialect {

    @Override
    public String columns(String dbName, String tableName) {
        throw new RuntimeException("不支持的数据库类型");
    }

    @Override
    public String tables(String dbName) {
        throw new RuntimeException("不支持的数据库类型");
    }

    @Override
    public String buildPaginationSql(String sql, long offset, long count) {
        throw new RuntimeException("不支持的数据库类型");
    }

    @Override
    public String count(String sql) {
        throw new RuntimeException("不支持的数据库类型");
    }

    @Override
    public RowMapper<DbColumn> columnMapper() {
        throw new RuntimeException("不支持的数据库类型");
    }

    @Override
    public RowMapper<DbTable> tableMapper() {
        throw new RuntimeException("不支持的数据库类型");
    }
}

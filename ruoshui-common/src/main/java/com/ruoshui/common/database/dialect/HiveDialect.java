package com.ruoshui.common.database.dialect;


import com.ruoshui.core.database.core.DbColumn;
import com.ruoshui.core.database.core.DbTable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class HiveDialect extends HiveAbstractDbDialect {
    @Override
    public RowMapper<DbColumn> columnMapper() {
        return (ResultSet rs, int rowNum) -> {
            DbColumn entity = new DbColumn();
            entity.setColName(rs.getString("col_name"));
            entity.setDataType(rs.getString("data_type"));
            entity.setColComment(rs.getString("comment"));
            return entity;
        };
    }

    @Override
    public RowMapper<DbTable> tableMapper() {
        return (ResultSet rs, int rowNum) -> {
            DbTable entity = new DbTable();
            entity.setTableName(rs.getString("tab_name"));
            return entity;
        };
    }
}

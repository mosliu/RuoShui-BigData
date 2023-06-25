package com.ruoshui.common.domian;

import java.util.List;

public class MySqlTable {

    private String database;

    private String tableName;

    private String createTableQuery;

    private String comment;

    private List<Column> ColumnList;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumnList() {
        return ColumnList;
    }

    public void setColumnList(List<Column> columnList) {
        ColumnList = columnList;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getCreateTableQuery() {
        return createTableQuery;
    }

    public void setCreateTableQuery(String createTableQuery) {
        this.createTableQuery = createTableQuery;
    }

    @Override
    public String toString() {
        return "CkTable{" +
                "tableName='" + tableName + '\'' +
                ", ColumnList=" + ColumnList +
                '}';
    }
}

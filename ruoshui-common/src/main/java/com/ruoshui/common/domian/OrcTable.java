package com.ruoshui.common.domian;

import java.util.List;

public class OrcTable {
    private String database;

    private String tableName;

    private String comments;

    private String createTableQuery;

    private List<Column> ColumnList;

    public String getCreateTableQuery() {
        return createTableQuery;
    }

    public void setCreateTableQuery(String createTableQuery) {
        this.createTableQuery = createTableQuery;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Column> getColumnList() {
        return ColumnList;
    }

    public void setColumnList(List<Column> columnList) {
        ColumnList = columnList;
    }

    @Override
    public String toString() {
        return "OrcTable{" +
                "database='" + database + '\'' +
                ", tableName='" + tableName + '\'' +
                ", comments='" + comments + '\'' +
                ", createTableQuery='" + createTableQuery + '\'' +
                ", ColumnList=" + ColumnList +
                '}';
    }
}

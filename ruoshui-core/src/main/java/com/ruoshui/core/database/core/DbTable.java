package com.ruoshui.core.database.core;

import lombok.Data;

@Data
public class DbTable {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;
}

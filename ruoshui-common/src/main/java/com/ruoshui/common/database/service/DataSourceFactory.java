package com.ruoshui.common.database.service;


import com.ruoshui.common.database.constants.DbQueryProperty;

public interface DataSourceFactory {

    /**
     * 创建数据源实例
     *
     * @param property
     * @return
     */
    public DbQuery createDbQuery(DbQueryProperty property);
}
